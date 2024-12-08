defmodule Day6 do
  def turn(coord) do
    case coord do
      {0, -1} -> {-1, 0}
      {-1, 0} -> {0, 1}
      {0, 1} -> {1, 0}
      {1, 0} -> {0, -1}
      _ -> nil
    end
  end

  def direction(s) do
    case s do
      "<" -> {0, -1}
      "^" -> {-1, 0}
      ">" -> {0, 1}
      "v" -> {1, 0}
      _ -> nil
    end
  end

  def move(maze, {x, y}, {vx, vy}, visited, changed_before) do
    # IO.puts("at #{x},#{y}, count is #{MapSet.size(visited)}")
    next = maze[x + vx][y + vy]
    new_visited = MapSet.put(visited, {x, y})

    changed_before =
      if MapSet.size(visited) != MapSet.size(new_visited) do
        0
      else
        changed_before + 1
      end

    visited = new_visited

    if changed_before > 1000 do
      {:loop, changed_before}
    else
      case next do
        nil ->
          {:ok, MapSet.size(visited)}

        "#" ->
          move(
            maze,
            {x, y},
            turn({vx, vy}),
            visited,
            changed_before
          )

        _ ->
          move(maze, {x + vx, y + vy}, {vx, vy}, visited, changed_before)
      end
    end
  end
end

maze = Utils.read_matrix("day6.actual")
maze_x = map_size(maze) - 1
maze_y = map_size(maze[0]) - 1
IO.puts("#{maze_x} #{maze_y}")

starting_point =
  for x <- 0..maze_x, y <- 0..maze_y do
    if maze[x][y] in ["<", ">", "^", "v"] do
      {x, y}
    else
      {nil, nil}
    end
  end
  |> Enum.filter(fn {x, _y} -> not is_nil(x) end)
  |> List.first()
  |> IO.inspect()

{x, y} = starting_point
starting_direction = Day6.direction(maze[x][y])

Day6.move(maze, starting_point, starting_direction, MapSet.new(), 0)
|> IO.inspect(label: "Part 1")

all_maps =
  for x <- 0..maze_x, y <- 0..maze_y do
    if maze[x][y] in ["#", "<", ">", "^", "v"] do
      nil
    else
      Map.put(maze, x, Map.put(maze[x], y, "#"))
    end
  end
  |> Enum.filter(fn m -> not is_nil(m) end)

all_maps
|> Enum.map(fn map ->
  Task.async(fn -> Day6.move(map, starting_point, starting_direction, MapSet.new(), 0) end)
end)
|> Enum.map(&Task.await/1)
|> Enum.filter(fn {status, _} -> :loop == status end)
|> Enum.count()
|> IO.inspect(label: "Part2")

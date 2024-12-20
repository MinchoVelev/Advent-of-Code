defmodule Day10 do
  def find_score(map, {x, y}) do
    find_target(map, MapSet.new(), [{x, y}], 0)
  end

  @directions [{-1, 0}, {0, -1}, {1, 0}, {0, 1}]

  def find_target(map, visited, queue, nines) do
    case queue do
      [{x, y} | tail] ->
        neighbors = find_neighbours(map, {x, y}, visited)

        nines =
          nines + (Enum.filter(neighbors, fn {nx, ny} -> map[nx][ny] == 9 end) |> Enum.count())

        find_target(map, MapSet.put(visited, {x, y, map[x][y]}), tail ++ neighbors, nines)

      [] ->
        {MapSet.filter(visited, fn {vx, vy, vv} -> vv == 9 end) |> Enum.count(), nines}
    end
  end

  def find_neighbours(map, {x, y}, visited) do
    @directions
    |> Enum.map(fn {vx, vy} -> {vx + x, vy + y} end)
    |> Enum.filter(fn {nx, ny} ->
      nx >= 0 and ny >= 0 and nx < map_size(map) and ny < map_size(map[0]) and
        map[x][y] + 1 == map[nx][ny] and
        not MapSet.member?(visited, {nx, ny, map[nx][ny]})
    end)
  end
end

map = Utils.read_matrix("day10.actual")

map =
  for x <- 0..(map_size(map) - 1), y <- 0..(map_size(map[0]) - 1), reduce: %{} do
    acc ->
      row = Map.get(acc, x, %{})
      row = Map.put(row, y, String.to_integer(map[x][y]))
      Map.put(acc, x, row)
  end

starts =
  for x <- 0..(map_size(map) - 1), y <- 0..(map_size(map[0]) - 1), reduce: [] do
    acc ->
      if map[x][y] == 0 do
        acc ++ [{x, y}]
      else
        acc
      end
  end

Enum.map(starts, fn {sx, sy} -> Day10.find_score(map, {sx, sy}) end)
|> Enum.reduce({0, 0}, fn {unique, all}, {su, sa} -> {unique + su, all + sa} end)
|> IO.inspect(label: "{unique, all}")

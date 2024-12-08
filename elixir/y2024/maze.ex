defmodule MazeSolver do
  def bfs(maze, start_x, start_y, target) do
    IO.puts("Searching for [#{target}] starting from #{start_x}:#{start_y}")
    i_bfs(maze, [{start_x, start_y}], MapSet.new(), target)
  end

  def i_bfs(maze, [{x, y} | queue], visited, target) do
    current = maze[x][y]

    cond do
      not is_binary(current) ->
        i_bfs(maze, queue, visited, target)

      String.equivalent?(current, target) ->
        {:ok, {x, y}}

      {x, y} in visited ->
        i_bfs(maze, queue, visited, target)

      {x, y} not in visited ->
        new_visited = MapSet.put(visited, {x, y})

        new_queue =
          for i <- -1..1, j <- -1..1, i != 0 or j != 0, reduce: queue do
            acc -> List.insert_at(acc, -1, {x + i, y + j})
          end

        i_bfs(maze, new_queue, new_visited, target)
    end
  end

  def i_bfs(_maze, [], _visited, target) do
    {:error, "[#{target}] not found"}
  end
end

maze = Utils.read_matrix("day4.test")

MazeSolver.bfs(maze, 0, 0, ".") |> IO.inspect()
MazeSolver.bfs(maze, 0, 0, "p") |> IO.inspect()

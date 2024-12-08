defmodule Dijkstra do
  def shortest(graph, start, target) do
    IO.puts("Searching for shortest path to [#{target}] starting from #{start}")
    i_shortest(graph, [{start, 0}], Map.put(%{}, start, 0), MapSet.new(), target)
  end

  def i_shortest(graph, queue = [_ | _], distance_map, visited, target) do
    {{s_key, s_dist}, s_index} =
      Enum.with_index(queue)
      |> Enum.min_by(fn {{_, dist_a}, _} -> dist_a end)

    queue = List.delete_at(queue, s_index)

    cond do
      String.equivalent?(target, s_key) ->
        IO.inspect(distance_map)
        {:ok, "found #{s_key} in distance #{distance_map[s_key]}"}

      MapSet.member?(visited, s_key) ->
        i_shortest(graph, queue, distance_map, visited, target)

      true ->
        visited = MapSet.put(visited, s_key)
        nodes = graph[s_key]

        distance_map = update_distances(s_dist, nodes, distance_map)

        queue =
          for {n_key, n_dist} <- nodes, not MapSet.member?(visited, nodes), reduce: queue do
            acc -> acc ++ [{n_key, n_dist + s_dist}]
          end

        i_shortest(graph, queue, distance_map, visited, target)
    end
  end

  def i_shortest(_graph, [], distance_map, _visited, target) do
    IO.inspect(distance_map)
    {:error, "failed to find #{target}"}
  end

  def update_distances(s_dist, nodes, distance_map) do
    Enum.reduce(nodes, distance_map, fn {n_key, n_value}, acc ->
      Map.update(acc, n_key, n_value + s_dist, fn current_dist ->
        min(current_dist, n_value + s_dist)
      end)
    end)
  end
end

graph =
  for line <- Utils.stream_file("dijkstra.txt"),
      [a, b, d | _] = String.split(line),
      reduce: %{} do
    acc ->
      list_a = Map.get(acc, a, [])
      acc = Map.put(acc, a, list_a ++ [{b, String.to_integer(d)}])
      list_b = Map.get(acc, b, [])
      Map.put(acc, b, list_b ++ [{a, String.to_integer(d)}])
  end
  |> IO.inspect()

Dijkstra.shortest(graph, "a", "f") |> IO.inspect()
Dijkstra.shortest(graph, "a", "o") |> IO.inspect()

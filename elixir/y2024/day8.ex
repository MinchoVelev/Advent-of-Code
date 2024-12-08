defmodule Day8 do
  def extrapolate_points({x1, y1}, {x2, y2}, detection_range) do
    {direction_x, direction_y} = {x2 - x1, y2 - y1}

    for i <- detection_range, reduce: [] do
      acc ->
        p1 = {x1 - i * direction_x, y1 - i * direction_y}
        p2 = {x2 + i * direction_x, y2 + i * direction_y}
        acc ++ [p1, p2]
    end
  end

  def detect_antinodes(points_per_freq, acc, detection_range) do
    result =
      for left <- Enum.with_index(points_per_freq),
          right <- Enum.with_index(points_per_freq),
          elem(left, 1) < elem(right, 1),
          reduce: MapSet.new() do
        sacc ->
          {left_p, _} = left
          {right_p, _} = right
          antinodes = Day8.extrapolate_points(left_p, right_p, detection_range)
          MapSet.union(sacc, MapSet.new(antinodes))
      end

    MapSet.union(acc, result)
  end
end

map = Utils.read_matrix("day8.actual")

len_x = map_size(map)
len_y = map_size(map[0])

symbols_map =
  for x <- 0..(len_x - 1), y <- 0..(len_y - 1), reduce: %{} do
    acc ->
      symbol = map[x][y]

      if symbol == "." do
        acc
      else
        coord_list = Map.get(acc, symbol, [])
        coord_list = coord_list ++ [{x, y}]
        Map.put(acc, symbol, coord_list)
      end
  end

# Part 1

antinodes =
  Map.values(symbols_map)
  |> Enum.reduce(MapSet.new(), fn points_per_freq, acc ->
    Day8.detect_antinodes(points_per_freq, acc, [1])
  end)

Enum.filter(antinodes, fn {x, y} ->
  x >= 0 and y >= 0 and x < len_x and y < len_y
end)
|> Enum.count()
|> IO.inspect(label: "Part 1")

# Part 2
antinodes =
  Map.values(symbols_map)
  |> Enum.reduce(MapSet.new(), fn points_per_freq, acc ->
    Day8.detect_antinodes(points_per_freq, acc, 0..500)
  end)

Enum.filter(antinodes, fn {x, y} ->
  x >= 0 and y >= 0 and x < len_x and y < len_y
end)
|> Enum.count()
|> IO.inspect(label: "Part 2")

{mapped, next_count} =
  File.read!("inputs/day9.actual")
  |> String.replace("\n", "")
  |> String.graphemes()
  |> Enum.with_index()
  |> Enum.reduce({%{}, 0}, fn {s, i}, {acc, count} ->
    multiplier = String.to_integer(s)

    if multiplier == 0 do
      {acc, count}
    else
      number =
        if rem(i, 2) == 0 do
          div(i, 2)
        else
          -1
        end

      {new_map, next_count} =
        for i <- 1..multiplier, reduce: {%{}, count} do
          {multiplied, map_index} -> {Map.put(multiplied, map_index, number), map_index + 1}
        end

      {Map.merge(acc, new_map), next_count}
    end
  end)

reverse_index = map_size(mapped) - 1

{result, _} =
  for i <- 0..(map_size(mapped) - 1), reduce: {mapped, reverse_index} do
    {result_map, j} ->
      # IO.puts("looking at  i:#{i} and :#{j}. at i:#{result_map[i]} at j:#{result_map[j]}")

      if result_map[i] == -1 and j > i do
        # IO.puts("Needs swap")
        # forward the reverse
        j =
          for jm <- 1..9, result_map[j] == -1 and j > i, reduce: j do
            new_j ->
              if result_map[new_j] != -1 do
                new_j
              else
                new_j - 1
              end
          end

        # IO.puts("J after fast forward: #{j}")

        # swap
        if result_map[j] != -1 and j > i do
          tmp = Map.put(result_map, i, result_map[j])
          {Map.put(tmp, j, -1), j - 1}
        else
          {result_map, j}
        end
      else
        {result_map, j}
      end
  end

# for i <- 0..(map_size(result) - 1) do
#   IO.write(:stdio, "#{result[i]} ")
# end

for i <- 0..(map_size(result) - 1), reduce: 0 do
  acc ->
    if result[i] == -1 do
      acc
    else
      acc + result[i] * i
    end
end
|> IO.inspect(label: "part 1")

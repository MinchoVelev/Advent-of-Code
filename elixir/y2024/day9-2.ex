defmodule Day9 do
  def do_defrag(map) do
    defrag(map, map_size(map) - 1, MapSet.new())
  end

  def defrag(map, rindex, swapped) do
    if MapSet.member?(swapped, rindex) do
      # IO.puts("#{rindex} already swapped")
      defrag(map, rindex - 1, swapped)
    else
      end_i = rindex
      start = end_of_file(map, end_i)
      # IO.puts("\nNow at range #{start}-#{end_i}")

      cond do
        is_nil(end_i) or is_nil(start) or end_i == -1 ->
          # IO.puts("End reached")
          map

        true ->
          first_free = find_free(map, end_i - start, start)

          if map[start] == -1 or is_nil(first_free) do
            # IO.puts("Skip the interval")
            defrag(map, start - 1, swapped)
          else
            # IO.puts("Swap and continue")
            swapped_map = swap(map, start, end_i, first_free)

            # IO.inspect(first_free..(first_free + end_i - start),
            #   label: "Adding to already swapped"
            # )

            swapped =
              MapSet.union(MapSet.new(first_free..(first_free + end_i - start)), swapped)

            defrag(swapped_map, start - 1, swapped)
          end
      end
    end
  end

  def swap(map, start, end_i, first_free) do
    for i <- 0..(end_i - start), reduce: map do
      n_map ->
        # IO.puts("#putting #{start + i} on #{first_free + i}")
        tmp = Map.put(n_map, first_free + i, n_map[start + i])
        Map.put(tmp, start + i, -1)
    end
  end

  def find_free(map, length, limit) do
    0..(limit - length)
    |> Enum.find(nil, fn key ->
      for i <- key..(key + length), reduce: true do
        all_match -> all_match and map[i] == -1
      end
    end)
  end

  def end_of_file(map, prev_index) do
    {result_index, _} =
      for _i <- 0..8, reduce: {prev_index, false} do
        {current_index, found} ->
          cond do
            is_nil(current_index) ->
              {nil, false}

            current_index == 0 ->
              {0, true}

            found ->
              {current_index, found}

            current_index - 1 == 0 or map[current_index] != map[current_index - 1] ->
              {current_index, true}

            true ->
              {current_index - 1, false}
          end
      end

    result_index
  end
end

{mapped, _next_count} =
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
        for _i <- 1..multiplier, reduce: {%{}, count} do
          {multiplied, map_index} -> {Map.put(multiplied, map_index, number), map_index + 1}
        end

      {Map.merge(acc, new_map), next_count}
    end
  end)

defragged = Day9.do_defrag(mapped)

for i <- 0..(map_size(defragged) - 1), reduce: 0 do
  acc ->
    # IO.puts(defragged[i])

    if defragged[i] > -1 do
      acc + i * defragged[i]
    else
      acc
    end
end
|> IO.inspect(label: "Part 2")

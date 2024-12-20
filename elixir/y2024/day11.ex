defmodule Day11 do
  def process(map, count_left) do
    if count_left > 0 do
      map =
        for k <- Map.keys(map), reduce: %{} do
          new_map ->
            count = map[k]

            tmp_map =
              blink(k)
              |> Enum.reduce(%{}, fn n, acc ->
                old_count = Map.get(acc, n, 0)
                Map.put(acc, n, old_count + count)
              end)

            Map.merge(tmp_map, new_map, fn _k, v1, v2 -> v1 + v2 end)
        end

      process(map, count_left - 1)
    else
      Enum.sum(Map.values(map))
    end
  end

  def blink(n) do
    cond do
      n == 0 ->
        [1]

      rem(length(Integer.digits(n)), 2) == 0 ->
        s = Integer.to_string(n)
        s_size = String.length(s)
        {a, b} = String.split_at(s, div(s_size, 2))
        [a, b] |> Enum.map(&String.to_integer/1)

      true ->
        [n * 2024]
    end
  end
end

list =
  File.read!("inputs/day11.actual")
  |> String.replace("\n", "")
  |> String.split()
  |> Enum.map(&String.to_integer/1)

list
|> Enum.map(fn i -> Day11.process(%{i => 1}, 25) end)
|> Enum.sum()
|> IO.inspect(label: "Part1")

list
|> Enum.map(fn i -> Day11.process(%{i => 1}, 75) end)
|> Enum.sum()
|> IO.inspect(label: "Part2")

# list
# |> Enum.map(fn i -> [i] end)
# |> Enum.map(fn il -> length(Day11.process(il, 75)) end)
# |> Enum.sum()
# |> IO.inspect(label: "Part2")

defmodule Day5 do
  def swap(a, i1, i2) do
    a = :array.from_list(a)

    v1 = :array.get(i1, a)
    v2 = :array.get(i2, a)

    a = :array.set(i1, v2, a)
    a = :array.set(i2, v1, a)

    :array.to_list(a)
  end

  def order(wrong, pairs) do
    wrong
    |> Enum.map(fn line ->
      Enum.reduce(pairs, line, fn p, acc ->
        [before, af | _] = String.split(p, "|")
        i = Enum.find_index(acc, fn s -> String.equivalent?(s, before) end)
        af_index = Enum.find_index(acc, fn s -> String.equivalent?(s, af) end)

        cond do
          is_nil(i) or is_nil(af_index) ->
            acc

          af_index > i ->
            acc

          true ->
            acc = List.delete_at(acc, af_index)
            List.insert_at(acc, i, af)
        end
      end)
    end)
  end
end

f = Path.absname("inputs/day5.actual")

[pairs, updates] =
  for f <- String.split(File.read!(f), "\n\n") do
    String.split(f, "\n")
  end
  |> Enum.filter(fn l -> length(l) > 0 end)

wrong =
  updates
  |> Enum.filter(fn s -> String.length(s) > 0 end)
  |> Enum.map(fn u -> String.split(u, ",") end)
  |> Enum.map(fn u ->
    for {number, index} <- Enum.with_index(u) do
      numbers_after =
        Enum.filter(pairs, fn p -> String.starts_with?(p, number <> "|") end)
        |> Enum.map(fn p ->
          [_ | t] = String.split(p, "|")
          List.first(t)
        end)

      matches =
        for af <- numbers_after do
          af_index = Enum.find_index(u, fn a -> String.equivalent?(a, af) end)
          result = is_nil(af_index) or index < af_index
          # IO.puts("#{number} is at #{index}, is before #{af}: #{result}")
          result
        end

      false not in matches
    end
  end)
  |> Enum.with_index()
  |> Enum.filter(fn {l, _i} -> false in l end)
  |> Enum.map(fn {_l, i} ->
    Enum.at(updates, i)
  end)

wrong = Enum.map(wrong, fn sl -> String.split(sl, ",") end) |> IO.inspect()

for _j <- 1..100, reduce: wrong do
  acc ->
    acc = Day5.order(acc, pairs)

    acc
    |> Enum.map(fn line ->
      l = length(line)
      Enum.at(line, div(l, 2)) |> String.to_integer()
    end)
    |> Enum.sum()
    |> IO.inspect()

    acc
end

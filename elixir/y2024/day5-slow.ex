defmodule Day5 do
  def middle(l) do
    mid = length(l) |> div(2)
    Enum.at(l, mid) |> String.to_integer()
  end
end

f = Path.absname("inputs/day5.actual")

[pairs, updates] =
  for f <- String.split(File.read!(f), "\n\n") do
    String.split(f, "\n")
  end

updates =
  Enum.filter(updates, fn u -> String.length(u) > 0 end)
  |> Enum.map(fn u -> String.split(u, ",") end)
  |> Enum.filter(fn l -> length(l) > 0 end)

sorted =
  Enum.map(updates, fn u ->
    Enum.sort(u, fn left, right ->
      pair =
        Enum.filter(pairs, fn p ->
          matched =
            String.equivalent?(p, "#{left}|#{right}") or String.equivalent?(p, "#{right}|#{left}")

          matched
        end)

      [r_left | _] = String.split(List.first(pair), "|")

      cond do
        String.equivalent?(r_left, left) ->
          true

        true ->
          false
      end
    end)
  end)

compared =
  Enum.zip_reduce(updates, sorted, [], fn left, right, acc ->
    acc ++ [{left == right, left, right}]
  end)

# Part 1
Enum.filter(compared, fn {correct, _unsorted, _sorted} ->
  correct
end)
|> Enum.map(fn {_, unsorted, _sorted} -> Day5.middle(unsorted) end)
|> Enum.sum()
|> IO.inspect(label: "Part 1")

# Part 2
Enum.filter(compared, fn {correct, _unsorted, _sorted} ->
  not correct
end)
|> Enum.map(fn {_, _unsorted, sorted} -> Day5.middle(sorted) end)
|> Enum.sum()
|> IO.inspect(label: "Part 2")

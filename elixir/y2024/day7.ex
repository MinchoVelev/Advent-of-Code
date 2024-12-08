defmodule Day7 do
  def is_possible(test, numbers, part_two) do
    possible =
      Enum.reduce(numbers, [1], fn n, acc ->
        mul = Enum.map(acc, fn an -> an * n end)
        sum = Enum.map(acc, fn an -> an + n end)

        concat =
          if part_two do
            Enum.map(acc, fn an ->
              String.to_integer(Integer.to_string(an) <> Integer.to_string(n))
            end)
          else
            []
          end

        result = Enum.filter(mul ++ sum ++ concat, fn n -> n <= test end)
      end)
      |> Enum.any?(fn n -> n == test end)

    {test, possible}
  end
end

input =
  Utils.stream_file("day7.actual")
  |> Enum.map(fn line ->
    [test, numbers | _] = String.split(line, ":")
    numbers = String.split(String.trim(numbers))

    {String.to_integer(test), Enum.map(numbers, &String.to_integer/1)}
  end)

input
|> Enum.map(fn {test, numbers} -> Day7.is_possible(test, numbers, false) end)
|> Enum.filter(fn {_test, is_possible} -> is_possible end)
|> Enum.map(fn {test, _is_possible} -> test end)
|> Enum.sum()
|> IO.inspect(label: "Part 1")

input
|> Enum.map(fn {test, numbers} -> Task.async(fn -> Day7.is_possible(test, numbers, true) end) end)
|> Enum.map(&Task.await/1)
|> Enum.filter(fn {_test, is_possible} -> is_possible end)
|> Enum.map(fn {test, _is_possible} -> test end)
|> Enum.sum()
|> IO.inspect(label: "Part 2")

:erlang.memory(:total) |> IO.inspect()

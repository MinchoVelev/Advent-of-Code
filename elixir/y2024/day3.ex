defmodule Day3 do
  def read() do
    f = Path.absname("inputs/day3.txt")

    File.read!(f) |> String.replace("\n", "")
  end

  def day3_1() do
    calc_sum(read())
    |> IO.inspect()
  end

  def calc_sum(s) do
    Regex.scan(~r{mul\((\d+),(\d+)\)}, s)
    |> Enum.map(fn [_, left, right | _] -> String.to_integer(left) * String.to_integer(right) end)
    |> Enum.sum()
  end

  def day3_2() do
    input = "do()" <> read() <> "don't()"

    s =
      Regex.scan(~r{do\(\).*?don't\(\)}, input)
      |> Enum.map(fn l -> Enum.reduce(l, "", fn a, b -> a <> b end) end)
      |> Enum.reduce("", fn a, b -> a <> b end)

    calc_sum(s)
    |> IO.inspect()
  end
end

IO.puts(:part1)
start = NaiveDateTime.utc_now()
Day3.day3_1()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

IO.puts("\npart2")
start = NaiveDateTime.utc_now()
Day3.day3_2()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

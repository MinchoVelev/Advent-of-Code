defmodule Day3 do
  def read() do
    f = Path.absname("inputs/day3.test")

    File.stream!(f)
    |> Enum.map(&String.trim/1)
    |> Enum.map(fn s -> String.split(s) end)
    |> Enum.map(fn l -> Enum.map(l, &String.to_integer/1) end)
  end

  def day3_1() do
    read()
    |> IO.inspect()
  end

  def day3_2() do
  end
end

IO.puts(:part1)
start = NaiveDateTime.utc_now()
Day3.day3_1()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

# IO.puts("\npart2")
# start = NaiveDateTime.utc_now()
# Day3.day3_2()
# runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
# IO.puts("\nTook [#{runtime}] microseconds\n")

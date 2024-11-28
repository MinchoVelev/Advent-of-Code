defmodule Day1 do
  def day1_2() do
    f = Path.absname("inputs/day1.txt")

    File.read!(f)
    |> String.split("\n", trim: true)
    |> Enum.each(&IO.puts/1)
  end

  def day1_1() do
    f = Path.absname("inputs/day1.txt")

    File.read!(f)
    |> String.split("\n", trim: true)
    |> Enum.each(&IO.puts/1)
  end
end

IO.puts(:day1)
Day1.day1_1()
IO.puts(:day2)
Day1.day1_2()

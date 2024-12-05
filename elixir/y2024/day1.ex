defmodule Day1 do
  def read_two_lists() do
    f = Path.absname("inputs/day1.txt")

    File.stream!(f)
    |> Enum.map(&String.trim/1)
    |> Enum.map(fn s -> String.split(s, "   ") end)
    |> Enum.map(fn l -> Enum.map(l, &String.to_integer/1) end)
    |> Enum.map(&List.to_tuple/1)
    |> Enum.unzip()
  end

  def day1_1() do
    {left, right} = read_two_lists()

    left = Enum.sort(left)
    right = Enum.sort(right)

    sum =
      Enum.zip(left, right)
      |> Enum.map(fn {a, b} -> abs(a - b) end)
      |> Enum.sum()

    IO.puts("Sum is #{sum}")
  end

  def day1_2() do
    {first, second} = read_two_lists()

    freq = Enum.frequencies(second)

    sum =
      first
      |> Enum.map(fn n -> n * Map.get(freq, n, 0) end)
      |> Enum.sum()

    IO.puts(sum)
  end
end

IO.puts(:day1)
Day1.day1_1()

IO.puts("\nday2")
Day1.day1_2()

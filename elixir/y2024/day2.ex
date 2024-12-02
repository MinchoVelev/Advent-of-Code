defmodule Day2 do
  def read() do
    f = Path.absname("inputs/day2.txt")

    File.stream!(f)
    |> Enum.map(&String.trim/1)
    |> Enum.map(fn s -> String.split(s) end)
    |> Enum.map(fn l -> Enum.map(l, &String.to_integer/1) end)
  end

  def day2_1() do
    read()
    |> Enum.filter(&is_safe/1)
    |> Enum.count()
    |> IO.puts()
  end

  def is_safe(list) do
    first = Enum.at(list, 0)
    second = Enum.at(list, 1)

    order = first > second

    has_unsafe =
      Enum.chunk_every(list, 2, 1, :discard)
      |> Enum.map(fn [left, right | _] -> {abs(left - right), order == left > right} end)
      |> Enum.any?(fn {n, o} -> n > 3 or n < 1 or !o end)

    !has_unsafe
  end

  def day2_2() do
    read()
    |> Enum.filter(&has_any_safe/1)
    |> Enum.count()
    |> IO.puts()
  end

  def has_any_safe(list) do
    lastIndex = Enum.count(list) - 1

    Enum.map(-1..lastIndex, fn index -> sublist(list, index) end)
    |> Enum.any?(&is_safe/1)
  end

  def sublist(list, index) when index >= 0 do
    List.delete_at(list, index)
  end

  def sublist(list, index) when index < 0 do
    list
  end
end

IO.puts(:part1)
start = NaiveDateTime.utc_now()
Day2.day2_1()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

IO.puts("\npart2")
start = NaiveDateTime.utc_now()
Day2.day2_2()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

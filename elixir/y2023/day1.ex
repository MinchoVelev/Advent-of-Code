defmodule Day1 do
  def day1_1() do
    f = Path.absname("inputs/day1.txt")

    result =
      File.stream!(f)
      |> Enum.map(&String.trim/1)
      |> Enum.map(&extract_numbers/1)
      |> Enum.map(&extract_first_and_last/1)
      |> Enum.map(&String.to_integer/1)
      |> Enum.sum()

    IO.puts(result)
  end

  def extract_numbers(s) do
    s
    |> String.to_charlist()
    |> Enum.map(fn c -> c - ?0 end)
    |> Enum.filter(fn i -> i >= 0 && i <= 9 end)
    |> Enum.join("")
  end

  def extract_first_and_last(s) do
    String.first(s) <> String.last(s)
  end

  def day1_2() do
    f = Path.absname("inputs/day1.txt")

    result =
      File.stream!(f)
      |> Enum.map(&String.trim/1)
      |> Enum.map(&words_to_int/1)
      |> Enum.map(&extract_numbers/1)
      |> Enum.map(&extract_first_and_last/1)
      |> Enum.map(&String.to_integer/1)
      |> Enum.sum()

    IO.puts(result)
  end

  def words_to_int(s) do
    mappings = %{
      "oneight" => "18",
      "twone" => "21",
      "threeight" => "38",
      "fiveight" => "58",
      "eightwo" => "82",
      "eighthree" => "83",
      "nineight" => "98",
      "nine" => "9",
      "eight" => "8",
      "seven" => "7",
      "six" => "6",
      "five" => "5",
      "four" => "4",
      "three" => "3",
      "two" => "2",
      "one" => "1",
      "zero" => "0"
    }

    Enum.reduce(mappings, s, fn {key, value}, acc ->
      String.replace(acc, key, value)
    end)
  end
end

Day1.day1_1()
Day1.day1_2()

defmodule Day4 do
  @input_file "day4.actual"

  def day4_1() do
    matrix = Utils.read_matrix(@input_file)
    x_len = length(Map.keys(matrix))
    y_len = length(Map.keys(matrix[0]))

    for x <- 0..x_len, y <- 0..y_len do
      {x, y}
    end
    |> Enum.map(fn {x, y} -> count(matrix, x, y) end)
    |> Enum.sum()
    |> IO.inspect()
  end

  def count(map, x, y) do
    horizontal =
      Enum.map(0..3, fn n -> map[x][y + n] end)
      |> Enum.filter(fn s -> is_binary(s) end)
      |> Enum.reduce("", fn s, acc -> acc <> s end)

    vertical =
      Enum.map(0..3, fn n -> map[x + n][y] end)
      |> Enum.filter(fn s -> is_binary(s) end)
      |> Enum.reduce("", fn s, acc -> acc <> s end)

    left_diagonal =
      Enum.map(0..3, fn n -> map[x - n][y + n] end)
      |> Enum.filter(fn s -> is_binary(s) end)
      |> Enum.reduce("", fn s, acc -> acc <> s end)

    right_diagonal =
      Enum.map(0..3, fn n -> map[x + n][y + n] end)
      |> Enum.filter(fn s -> is_binary(s) end)
      |> Enum.reduce("", fn s, acc -> acc <> s end)

    Enum.filter(
      [horizontal, vertical, left_diagonal, right_diagonal],
      fn word -> word in ["XMAS", String.reverse("XMAS")] end
    )
    |> Enum.count()
  end

  def day4_2() do
    matrix = Utils.read_matrix(@input_file)
    x_len = length(Map.keys(matrix))
    y_len = length(Map.keys(matrix[0]))

    for x <- 0..x_len, y <- 0..y_len do
      {x, y}
    end
    |> Enum.map(fn {x, y} -> count_x(matrix, x, y) end)
    |> Enum.sum()
    |> IO.inspect()
  end

  def count_x(map, x, y) do
    letters = [
      map[x][y],
      map[x - 1][y - 1],
      map[x - 1][y + 1],
      map[x + 1][y - 1],
      map[x + 1][y + 1]
    ]

    missing = Enum.any?(letters, fn a -> not is_binary(a) end)
    here = not missing

    word =
      if here do
        Enum.reduce(letters, "", fn a, acc -> acc <> a end)
      else
        ""
      end

    if word in ["AMSMS", "ASSMM", "AMMSS", "ASMSM"] do
      1
    else
      0
    end
  end
end

IO.puts(:part1)
start = NaiveDateTime.utc_now()
Day4.day4_1()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

IO.puts("\npart2")
start = NaiveDateTime.utc_now()
Day4.day4_2()
runtime = NaiveDateTime.diff(NaiveDateTime.utc_now(), start, :microsecond)
IO.puts("\nTook [#{runtime}] microseconds\n")

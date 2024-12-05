defmodule Utils do
  def read_matrix(s) do
    f = Path.absname("inputs/#{s}")

    File.stream!(f)
    |> Enum.map(&String.trim/1)
    |> Enum.with_index()
    |> Enum.reduce(%{}, fn {line, line_index}, acc ->
      char_map =
        line
        |> String.graphemes()
        |> Enum.with_index(0)
        |> Enum.map(fn {a, b} -> {b, a} end)
        |> Enum.into(%{})

      Map.put(acc, line_index, char_map)
    end)
  end

  def stream_file(s) do
    f = Path.absname("inputs/#{s}")

    File.stream!(f)
    |> Enum.map(&String.trim/1)
  end
end

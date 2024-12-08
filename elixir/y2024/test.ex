"23, 45, 66,89, 100"
"1 89 2 45 17 23 19"
"89 45 23"
"1 2 17 23 45 89 19"
ordered = [23, 45, 66, 89, 100]

input = [1, 89, 2, 45, 17, 23, 19]

extracted = Enum.filter(input, fn n -> n in ordered end)

extracted =
  Enum.sort_by(extracted, fn n -> Enum.find_index(ordered, fn a -> n == a end) end)

[smallest | tail] = extracted
smallest_i = Enum.find_index(input, fn n -> n == smallest end)
result = Enum.filter(input, fn n -> n not in tail end) |> IO.inspect()

new_index = Enum.find_index(result, fn n -> n == smallest end)

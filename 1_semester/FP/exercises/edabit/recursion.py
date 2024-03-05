def maximum(lst):
    if not lst: return None
    if len(lst) == 1: return lst[0]
    return lst[-1] if lst[-1] > maximum(lst[:-1]) else maximum(lst[:-1]) 

print(maximum([0, 12, 4, 87, 8]))


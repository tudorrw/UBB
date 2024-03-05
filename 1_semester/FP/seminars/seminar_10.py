def summe_gerade(number):
    if not number:
        return 0
    return summe_gerade(number//10) + number % 10 if not number % 2 else summe_gerade(number//10)


def letzte_großbuchstabe(txt):
    if not txt:
        return "Not found"
    return txt[-1] if txt[-1].isalpha() and txt[-1].isupper() else letzte_großbuchstabe(txt[:-1])


def vowels(txt):
    if not txt:
        return 0
    return 1 + vowels(txt[:-1]) if txt[-1] in 'aeiou' else vowels(txt[:-1])


def palindrom_string(txt):
    if len(str(txt)) <= 1:
        return True
    return palindrom_string(txt[1:-1]) if txt[0] == txt[-1] else False


def max_number(lst):
    if not lst: return None
    if len(lst) == 1: return lst[0]
    return max_number(lst[:-1]) if lst[-1] < max_number(lst[:-1]) else lst[-1]


def summ(lst):
    if type(lst) is int:
        return lst
    if len(lst) < 2:
        return summ(lst[0])
    mid = len(lst) // 2
    return summ(lst[:mid]) + summ(lst[mid:]) 







def merge_sort(l1, l2):
    l = []
    i = j = 0
    while(i < len(l1) and j < len(l2)):
        if l1[i] < l2[j]:
            l.append(l1[i])
            i += 1
        else:
            l.append(l2[j])
            j += 1

    l += l1[i:]
    l += l2[j:]
    return l


def boolean_sorted_list(lst):
    if not lst:
        return []
    return [0] + boolean_sorted_list(lst[:-1]) if not lst[-1] else boolean_sorted_list(lst[:-1]) + [1]
    
# from Vorlesung 9 ubungen
def hoch(a, b):
    if not b:
        return 1
    if b > 0:
        return a * hoch(a, b - 1)
    else:
        return 1 / a * hoch(a, b + 1)

def sortiert_lst(lst):
    if not lst:
        return True
    if len(lst) == 1:
        return 
    return sortiert_lst(lst[:-1]) if lst[-1] >= sortiert_lst(lst[:-1]) else False

if __name__ == '__main__':
    print(summe_gerade(1352438))
    print(letzte_großbuchstabe("UYvYUYyuAyuv"))
    print(vowels('rebecalaei'))
    print(palindrom_string('1221')) #aber fur strings
    print(max_number([1, 4, 8, 1, 5, 2, 6, 3]))
    print(summ([5, 6, [8, [9]], 7]))
    print("********************************")
    print(merge_sort([2, 4, 5, 5, 9, 11, 34, 56], [3, 4, 5, 6, 7, 9]))
    print(boolean_sorted_list([0, 1, 0, 1, 1, 0, 1, 0, 0]))
    print(hoch(5, -4), 5 ** -4)
    print(sortiert_lst([1, 5, 7, 9]))

    # https://ubbcluj.sharepoint.com/sites/GrundlagenderProgrammierung2022-2023/Class%20Materials/Folien/2022.V.9.pdf?CT=1675245418672&OR=ItemsView
    # https://ubbcluj.sharepoint.com/sites/GrundlagenderProgrammierung2022-2023/Class%20Materials/Folien/2022.V.10-11.pdf?CT=1675245427695&OR=ItemsView
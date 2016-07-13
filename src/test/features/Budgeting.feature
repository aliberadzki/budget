# plik zawiera spis testów akceptacyjnych, będących dla mnie samoego pierwotnym punktem definiowania wymagan
Feature: Użytkownik definiuje budzet (przewidywania, planuje)

# Jako uzytkownik
# Chcę móc zdefiniować strukturę budżetu (wydatki, przychody inwestycje)
# Aby móc rejestrować i planować przepływy pieniędzy

# definiuję w aplikacji kategorie oraz podkategorie wydatków i przychodów i inwestycji.

# Definiuje budzety miesieczne, (a moze i roczne, tygodniowe?) na poszczegolne kategorie wydatkow.
#Background:
#  Given Zdefiniowalem miesięczny budżet bazowy
#  And: Dodałem do niego kategorię przychodu Wynagrodzenie
#  And: Dodałem do niego kategorię wydatku Jedzenie
##  And: Dodałem do niego kategorię wydatku Mieszkanie
#  And: Dodałem do niego kategorię oszczędności Poduszka
##  And: Dodałem do niego kategorię oszczędności Emerytura
#  And: Do kategorii wydatku Jedzenie dodałem podkategorię Jedzenie w domu
#  And: Do kategorii wydatku Jedzenie dodałem podkategorię Jedzenie na mieście
##  And: Do kategorii wydatku Mieszkanie dodałem podkategorię Czynsz
#  And: Do kategorii wydatku Mieszkanie dodałem podkategorię Prąd
#  And: Wydatki na Jedzenie w domu szacuję na 1000
#  And: Wydatki na Jedzenie na mieście szacuję na 500
#  And: Wydatki na Czynsz szacuję na 500
#  And: Wydatki na Prąd szacuję na 300
#  And: Przychody z Wynagrodzenie szacuję na 10000
#  And: Inwestycje na Poduszka szacuję na 2000
#  And: Inwestycje na Emerytura szacuję na 2000

Scenario: Tworzenie kategorii
  Given Jest utworzony budzet bazowy
  And Nie ma w nim żadnych kategorii wydatków
  When Utworzę kategorie wydatków o nazwie: Jedzenie, Mieszkanie, Samochód
  Then Budżet powinien posiadać 3 kategorie wydatku
  And Budżet powinien zawierać kategorie: Jedzenie, Mieszkanie, Samochód

Scenario: Próba utworzenia istniejącej kategorii
  Given Jest utworzony budzet bazowy
  And Jest w nim kategoria wydatków o nazwie Jedzenie
  When Utworzę kategorie wydatków o nazwie: Jedzenie
  Then Budżet powinien posiadać 1 kategorię wydatku
  And Powinien zostać zwrócony błąd

  # -----------------------------------------------------------
# Budzet moze byc modyfikowany o nastepujacy sposob:
#  Scenario: Modyfikacja wydatku od dziś
#  Given: Wydatki na Jedzenie w domu od dziś szacuję na 1200
#- od tego okresu planowane wydatki na kategorie x to y
#- w tym okresie planowane wydatki na kategorie x to y

# Definiuje cele inwestycyjne (horyzont czasowy, cel - kwota)

# Definiuje zdrodla i cele przeplywow pienieznych (chodzi tu glownie o konta, lokaty etc)

# Definiuje stałe wydatki w przedziałach i interwałach czasowych o znanych (np. ZUS, multisport etc) i nieznanych (telefon) kwotach.
# Wydatki o nieznanych kwotach mogą mieć wartość orientacyjną. Aplikacja powinna mieć mechanizm przypominania o płatnościach,
# a płatność sama w sobie powinna mieć informację o pilności (np. ZUS jest nieprzekraczalny)

# Zapisuje wydatki pojedyncze (i inwestycje) kwalifikując je do odpowiednich kategorii.

# Zapisuje rzeczywiste przychody korygujace budzet (np ciezko przewidziec wysokosc wyplaty za 4 miesiace, ale np wiem ze ok 10k)

# Kazdy wydatek moze byc zakwalifikowany jako koszt (wymagana est wowczas klsyfikacja ile vatu), fajnie byloby moc zalaczyc dokument

# W kazdym momencie jestem w stanie wygenerowac raport za dany okres z metrykami pokazujacymi zrealizowanie budzetu z danego okresu

# Mogę przygotować 'zamknięcie okresu', gdzie np mam manko 500 zł (kasa która się rozeszła), a weryfikuję
# tylko stan konta. ew. manko traktuję jako niedoszacowanie/niedokładność w śledzeniu wydatków. Ew. nadwyzke jako przeszacowanie wydatkow/niedokladnosc w sledzeniu wydatkow.
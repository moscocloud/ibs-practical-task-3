# language: ru

@withdrawal
Функция: Таблица продуктов с функцией добавления продуктов

  @regress
  Сценарий: Проверка добавления товара
    * Проверка открытия сайта по заголовку
    * Нажатие на кнопку "Добавить"
    * Проверка открытия и содержания модального окна
    * Ввод в поле наименование "Манго"
    * Выбор типа продукта "Фрукт"
    * Выбор значения чекбокса "true"
    * Сохранение продукта
    * Проверка строки таблицы с параметрами: Наименование - "Манго", Тип - "Фрукт", Экзотический - "true"
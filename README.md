# AikamTestTask

Программа считывает данные из json файла и в зависимости от параметра поиска формирует новый json файл с результатами поиска из БД.

Программе нужно передать три аргумента:
- Тип поиска search или stat
- Путь до входного json файла
- Путь до выходного json файла

Запустить jar файл можно с помощью консольной команды.
Пример запуска  java -jar program.jar search input.json output.json 

В корне проекта лежит дамп БД shop.sql
Файлы с входными данными и jar файл лежат AikamTestTask/out/artifacts.
Там же лежит файл DbLoginParam.json. 
В нем нужно указать свой url БД, логин и пароль для соединения с БД.

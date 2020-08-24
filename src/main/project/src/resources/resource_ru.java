package src.resources;

import java.util.ListResourceBundle;


public class resource_ru extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    //AUTHORIZATION WINDOW
                    {"loginLabel", "Логин"},
                    {"passwordLabel", "Пароль"},
                    {"registerButton", "Регистрация"},
                    {"loginButton", "Вход"},

                    //COMMON
                    {"name", "Имя Продукта"},
                    {"coordinateX", "Координата X"},
                    {"coordinateY", "Координата Y"},
                    {"price", "Цена"},
                    {"creationDate", "Дата Создания"},
                    {"partNumber", "Количество"},
                    {"unitOfMeasure", "Единица Измерения"},
                    {"personName", "Имя Владельца"},
                    {"personHeight", "Высота Владельца"},
                    {"personEyeColor", "Цвет Глаз Владельца"},
                    {"locationName", "Название Локации"},
                    {"locationX", "Координата X Локации"},
                    {"locationY", "Координата Y Локации"},
                    {"locationZ", "Координата Z Локации"},
                    {"creator", "Создатель"},

                    {"PCS", "ШТУКА"},
                    {"MILLILITERS", "МИЛЛИЛИТРЫ"},
                    {"GRAMS", "ГРАММЫ"},

                    {"ШТУКА", "PCS"},
                    {"МИЛЛИЛИТРЫ", "MILLILITERS",},
                    {"ГРАММЫ", "GRAMS",},

                    {"RED", "КРАСНЫЙ"},
                    {"BLUE", "СИНИЙ"},
                    {"ORANGE", "ОРАНЖЕВЫЙ"},
                    {"WHITE", "БЕЛЫЙ"},
                    {"BROWN", "КОРИЧНЕВЫЙ"},

                    {"КРАСНЫЙ", "RED"},
                    {"СИНИЙ", "BLUE"},
                    {"ОРАНЖЕВЫЙ", "ORANGE"},
                    {"БЕЛЫЙ", "WHITE"},
                    {"КОРИЧНЕВЫЙ", "BROWN"},

                    {"applyButton", "Принять"},

                    //ADD WINDOW
                    {"addRadioButton", "Добавить"},
                    {"addIfMaxRadioButton", "Добавить Если Максимальный"},
                    {"addIfMinRadioButton", "Добавить Если Минимальный"},
                    {"cancelButton", "Отменить"},

                    // WORKSPACE
                    {"logoutButton", "Выйти"},
                    {"addButton", "Добавить Элемент"},
                    {"infoButton", "Информация"},
                    {"removeButton", "Удалить Элемент"},
                    {"historyButton", "История"},
                    {"executeScriptButton", "Запустить Скрипт"},
                    {"clearButton", "Очистить Коллекцию"},
                    {"helpButton", "Помощь"},
                    {"collectionTab", "Коллекция"},
                    {"visualizationTab", "Визуалиазация"},
                    {"sortItem", "Сортировать"},
                    {"reverseSortItem", "Сортировать В Обратном Порядке"},
                    {"undoItem", "Вернуть Прежнее состояние"},
                    {"filterItem", "Фильтровать"},
                    {"deleteItem", "Удалить"},
                    {"usernameLabel", "Пользователь:"},
                    {"languageButton", "Сменить Язык"},

                    //Path Window
                    {"openButton", "Открыть Проводник"},
                    {"pathLabel", "Путь К Файлу"},

                    //LANG WINDOW
                    {"russianRadioButton", "Русский"},
                    {"norwegianRadioButton", "Norsk"},
                    {"panamanianRadioButton", "Panameño"},
                    {"catalanRadioButton", "Català"},

                    //MESSAGES
                    {"Authorization is successful!", "Авторизация прошла успешно!"},
                    {"The user with this login and password does not exist", "Пользователь с данным логином и паролем не существует"},
                    {"Registration is successful!", "Регистрация прошла успешно!"},
                    {"The login is already exist", "Логин занят."},
                    {"A new script was started to execute\n", "Скрипт был успешно запущен!\n"},
                    {"The result of filtering by unit of measure:\n", "Результат фильрации по размерности:\n"},
                    {"The history of your last used commands:\n", "История ранее используемых команд:\n"},
                    {"Type can not be defined because collection is empty!\n", "Тип коллекции не может быть определен, так как коллекция пуста.\n"},
                    {"Problem with general class. Can not find type of class!", "Возникла проблема с главным классом. Невозможно найти тип класса!\n"},
                    {"Element was successfully removed.\n", "Элемент был успешно удален.\n"},
                    {"You don't have a permission to change this element!\n", "У Вас нет прав для изменения этого элемента!\n"},
                    {"The element with this id wasn't found.\n", "Элемент с таким id не найден!\n"},
                    {"The element's id was successfully updated!\n", "Элемент был успешно обновлен!\n"},
                    {"Some problems with adding a product.", "Возникли проблемы с добавлением продукта!\n"},
                    {"Product was successfully added to the collection.\n", "Продукт был успешно добавлен!\n"},
                    {"There are some problems with adding a product to collection.\n", "Возникли проблемы при добавлении продукта в коллекцию!\n"},
                    {"You are trying to add the product which isn't a min!\n", "Продукт не минимальный по цене!\n"},
                    {"You are trying to add the product which isn't a max!\n", "Продукт не максимальный по цене!\n"},

                    {"helpText", "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\" \n" +
                            "                        \"show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\" \n" +
                            "                        \"add {element} : добавить новый элемент в коллекцию\" \n" +
                            "                        \"update id {element} : обновить значение элемента коллекции, id которого равен заданному\" \n" +
                            "                        \"remove_by_id id : удалить элемент из коллекции по его id\" \n" +
                            "                        \"clear : очистить коллекцию\" \n" +
                            "                        \"save : сохранить коллекцию в файл\" \n" +
                            "                        \"execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме\" \n" +
                            "                        \"exit : завершить программу (без сохранения в файл)\" \n" +
                            "                        \"add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции\" \n" +
                            "                        \"add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\" \n" +
                            "                        \"history : вывести последние 11 команд (без их аргументов)\" \n" +
                            "                        \"filter_by_unit_of_measure unitOfMeasure : вывести элементы, значение поля unitOfMeasure которых равно заданному\" \n" +
                            "                        \"print_unique_part_number partNumber : вывести уникальные значения поля partNumber\" \n" +
                            "                        \"print_field_descending_owner owner : вывести значения поля owner в порядке убывания\\n"},
};

    public Object[][] getContents() {
        return contents;
    }
}

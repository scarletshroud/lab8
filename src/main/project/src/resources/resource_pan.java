package src.resources;

import java.util.ListResourceBundle;

public class resource_pan extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"loginLabel", "Iniciar sesión"},
                    {"passwordLabel", "Contraseña"},
                    {"registerButton", "Registrarse"},
                    {"loginButton", "Entrada"},

                    //COMMON
                    {"name", "Nombre del Producto"},
                    {"coordinateX", "Coordinar X"},
                    {"coordinateY", "Coordinar Y"},
                    {"price", "Precio"},
                    {"creationDate", "Fecha de creación"},
                    {"partNumber", "Сantidad"},
                    {"unitOfMeasure", "Unidad"},
                    {"personName", "Nombre del Dueño"},
                    {"personHeight", "Altura del Propietario"},
                    {"personEyeColor", "Color de Ojos del Propietario"},
                    {"locationName", "Nombre del Lugar"},
                    {"locationX", "Coordenada X de ubicación"},
                    {"locationY", "Coordenada Y de ubicación"},
                    {"locationZ", "Coordenada Z de ubicación"},
                    {"creator", "Creador"},

                    {"PCS", "COSA"},
                    {"MILLILITERS", "MILILITROS"},
                    {"GRAMS", "GRAMOS"},

                    {"COSA", "PCS"},
                    {"MILILITROS", "MILLILITERS"},
                    {"GRAMOS", "GRAMS"},

                    {"RED", "Rojo"},
                    {"BLUE", "Azul"},
                    {"ORANGE", "Naranja"},
                    {"WHITE", "Blanco"},
                    {"BROWN", "Marrón"},

                    {"Rojo", "RED"},
                    {"Azul", "BLUE"},
                    {"Naranja", "ORANGE"},
                    {"Blanco", "WHITE"},
                    {"Marrón", "BROWN"},

                    {"applyButton", "Aceptar"},

                    //ADD WINDOW
                    {"addRadioButton", "Añadir"},
                    {"addIfMaxRadioButton", "Agregar si máximo"},
                    {"addIfMinRadioButton", "Agregar si es mínimo"},
                    {"cancelButton", "Cancelar"},

                    //Path Window
                    {"openButton", "Explorador abierto"},
                    {"pathLabel", "Ingrese la ruta del archivo"},

                    // WORKSPACE
                    {"logoutButton", "Desconectarse"},
                    {"addButton", "Añadir artículo"},
                    {"infoButton", "Información"},
                    {"removeButton", "Eliminar elemento"},
                    {"historyButton", "Historia"},
                    {"executeScriptButton", "Ejecutar guión"},
                    {"clearButton", "Colección clara"},
                    {"helpButton", "Ayuda"},
                    {"collectionTab", "Colección"},
                    {"visualizationTab", "Visualización"},
                    {"sortItem", "Ordenar"},
                    {"reverseSortItem", "Ordenar en orden inverso"},
                    {"undoItem", "Volver al estado anterior"},
                    {"filterItem", "Filtrar"},
                    {"deleteItem", "Eliminar"},
                    {"usernameLabel", "Usuario:"},
                    {"languageButton", "Cambiar idioma"},

                    {"russianRadioButton", "Русский"},
                    {"norwegianRadioButton", "Norsk"},
                    {"panamanianRadioButton", "Panameño"},
                    {"catalanRadioButton", "Català"},

                    {"A new script was started to execute\n", "El script se lanzó con éxito!\n"},
                    {"The result of filtering by unit of measure:\n", "Resultado de filtración dimensional:\n"},
                    {"The history of your last used commands:\n", "Historial de comandos utilizados anteriormente:\n"},
                    {"Type can not be defined because collection is empty!\n", "El tipo de colección no se puede determinar porque la colección está vacía.\n"},
                    {"Problem with general class. Can not find type of class!", "Hubo un problema con la clase principal. ¡No se puede encontrar el tipo de clase!\n"},
                    {"Element was successfully removed.\n", "El artículo ha sido eliminado exitosamente.\n"},
                    {"You don't have a permission to change this element!\n", "No tiene autorización para modificar este artículo!\n"},
                    {"The element with this id wasn't found.\n", "No se encontró un elemento con esta identificación!\n"},
                    {"The element's id was successfully updated!\n", "El artículo ha sido actualizado con éxito!\n"},
                    {"Some problems with adding a product.", "Tengo problemas para agregar un producto a la colección!\n"},
                    {"Product was successfully added to the collection.\n", "El producto ha sido agregado con éxito!\n"},
                    {"There are some problems with adding a product to collection.\n", "Tengo problemas para agregar un producto a la colección!\n"},
                    {"You are trying to add the product which isn't a min!\n", "El producto no es el precio más bajo!\n"},
                    {"You are trying to add the product which isn't a max!\n", "El producto no es el precio máximo!\n"},


                    {"helpText", "info : muestra información sobre la colección (tipo, fecha de inicialización, número de elementos, etc.) en la secuencia de salida estándar\" \n" +
                            "                        \"show : salida a la secuencia de salida estándar todos los elementos de la colección en una representación de cadena\" \n" +
                            "                        \"add {element} : agregar nuevo elemento a la colección\" \n" +
                            "                        \"update id {element} : actualizar el valor de un elemento de colección cuyo id es igual al especificado\" \n" +
                            "                        \"remove_by_id id : eliminar un elemento de la colección por su id\" \n" +
                            "                        \"clear : colección clara\" \n" +
                            "                        \"save : guardar colección en archivo\" \n" +
                            "                        \"execute_script file_name : lee y ejecuta el script desde el archivo especificado. El script contiene comandos en la misma forma en que el usuario los ingresa de forma interactiva\" \n" +
                            "                        \"exit : finalizar el programa (sin guardar en un archivo)\" \n" +
                            "                        \"add_if_max {element} : agregar un nuevo elemento a la colección si su valor excede el valor del elemento más grande en esta colección\" \n" +
                            "                        \"add_if_min {element} : agregar un nuevo elemento a la colección si su valor es menor que el del elemento más pequeño en esta colección\" \n" +
                            "                        \"history : imprime los últimos 11 comandos (sin sus argumentos)\" \n" +
                            "                        \"filter_by_unit_of_measure unitOfMeasure : mostrar elementos cuyo valor de campo unitOfMeasure es igual al valor dado\" \n" +
                            "                        \"print_unique_part_number partNumber : imprime valores únicos para el campo partNumber\" \n" +
                            "                        \"print_field_descending_owner owner : imprimir los valores del campo del propietario en orden descendente\\n"},
            };

    public Object[][] getContents() {
        return contents;
    }
}

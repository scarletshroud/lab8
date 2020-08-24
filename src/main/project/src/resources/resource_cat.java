package src.resources;

import java.util.ListResourceBundle;

public class resource_cat extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"loginLabel", "Iniciar Sessió"},
                    {"passwordLabel", "Contrasenya"},
                    {"registerButton", "Registrar"},
                    {"loginButton", "Entrada"},

                    //COMMON
                    {"name", "Produktnavn"},
                    {"coordinateX", "Coordinar X"},
                    {"coordinateY", "Coordinar Y"},
                    {"price", "Preu"},
                    {"creationDate", "Data de creació"},
                    {"partNumber", "Quantitat"},
                    {"unitOfMeasure", "Unitat"},
                    {"personName", "Nom del propietari"},
                    {"personHeight", "Alçada del propietari"},
                    {"personEyeColor", "Propietari Color d'ulls"},
                    {"locationName", "Nom de la ubicació"},
                    {"locationX", "X Ubicació"},
                    {"locationY", "Y Ubicació"},
                    {"locationZ", "Z Ubicació"},
                    {"creator", "Creador"},

                    {"PCS", "Cosa"},
                    {"MILLILITERS", "Mil·lilitres"},
                    {"GRAMS", "GRAMES"},

                    {"Cosa", "PCS"},
                    {"Mil·lilitres", "MILLILITERS"},
                    {"GRAMES", "GRAMS"},

                    {"RED", "VERMELL"},
                    {"BLUE", "BLAU"},
                    {"ORANGE", "TARONJA"},
                    {"WHITE", "BLANC"},
                    {"BROWN", "MARRÓ"},

                    {"VERMELL", "RED"},
                    {"BLAU", "BLUE"},
                    {"TARONJA", "ORANGE"},
                    {"BLANC", "WHITE"},
                    {"MARRÓ", "BROWN"},


                    {"applyButton", "Per acceptar"},

                    //ADD WINDOW
                    {"addRadioButton", "Afegiu"},
                    {"addIfMaxRadioButton", "Afegeix si màxim"},
                    {"addIfMinRadioButton", "Afegeix si mínim"},
                    {"cancelButton", "Cancel · lar"},

                    //Path Window
                    {"openButton", "Explorador obert"},
                    {"pathLabel", "Introduïu la ruta del fitxer"},

                    // WORKSPACE
                    {"logoutButton", "Sortir"},
                    {"addButton", "Afegeix un element"},
                    {"infoButton", "Informació"},
                    {"removeButton", "Suprimeix l'element"},
                    {"historyButton", "Història"},
                    {"executeScriptButton", "Executeu script"},
                    {"clearButton", "Col·lecció neta"},
                    {"helpButton", "Ajuda"},
                    {"collectionTab", "Col · lecció"},
                    {"visualizationTab", "Visualització"},
                    {"sortItem", "Ordena"},
                    {"reverseSortItem", "Ordenar en ordre invers"},
                    {"undoItem", "Tornar a l'estat antic"},
                    {"filterItem", "Filtre"},
                    {"deleteItem", "Suprimeix"},
                    {"usernameLabel", "Usuari:"},
                    {"languageButton", "Canviar d'idioma"},

                    {"russianRadioButton", "Русский"},
                    {"norwegianRadioButton", "Norsk"},
                    {"panamanianRadioButton", "Panameño"},
                    {"catalanRadioButton", "Català"},

                    {"A new script was started to execute\n", "El script s'ha llançat correctament.\n"},
                    {"The result of filtering by unit of measure:\n", "Resultat de filtració dimensional:\n"},
                    {"The history of your last used commands:\n", "Historial d’ordres usades anteriorment:\n"},
                    {"Type can not be defined because collection is empty!\n", "El tipus de col·lecció no es pot determinar perquè la col·lecció està buida.\n"},
                    {"Problem with general class. Can not find type of class!", "Hi va haver un problema amb la classe principal. No es pot trobar el tipus de classe.\n"},
                    {"Element was successfully removed.\n", "L'element s'ha suprimit correctament.\n"},
                    {"You don't have a permission to change this element!\n", "No esteu autoritzat a modificar aquest article.\n"},
                    {"The element with this id wasn't found.\n", "No s'ha trobat cap element amb aquest id.\n"},
                    {"The element's id was successfully updated!\n", "L'article s'ha actualitzat correctament.\n"},
                    {"Some problems with adding a product.", "Tens problemes per afegir un producte!\n"},
                    {"Product was successfully added to the collection.\n", "S'ha afegit el producte correctament.\n"},
                    {"There are some problems with adding a product to collection.\n", "Tens problemes per afegir un producte a la col·lecció.\n"},
                    {"You are trying to add the product which isn't a min!\n", "El producte no és el preu més baix!\n"},
                    {"You are trying to add the product which isn't a max!\n", "El producte no és el preu màxim.\n"},


                    {"helpText", "info : mostra informació sobre la col·lecció (tipus, data d'inicialització, nombre d'elements, etc.) al flux de sortida estàndard\" +\n" +
                            "                        \"show : la sortida a la sortida estàndard emet tots els elements de la col·lecció en una representació de cadenes\" +\n" +
                            "                        \"add {element} : afegir un nou element a la col·lecció\" \n" +
                            "                        \"update id {element} : actualitzar el valor de l'element de la col·lecció, id que és igual al donat\" +\n" +
                            "                        \"remove_by_id id : elimina un article de la col·lecció per la sevaid\" \n" +
                            "                        \"clear : recollida clara\" \n" +
                            "                        \"save : desar la col·lecció al fitxer\" \n" +
                            "                        \"execute_script file_name : llegir i executar l'script des del fitxer especificat. L'script conté ordres en el mateix formulari en què són introduïdes per l'usuari de forma interactiva\" \n" +
                            "                        \"exit : finalitza el programa (sense guardar en un fitxer)\" \n" +
                            "                        \"add_if_max {element} : afegir un nou element a la col·lecció si el seu valor supera el valor de l'element més gran d'aquesta col·lecció\" \n" +
                            "                        \"add_if_min {element} : afegeix un element nou a la col·lecció si el seu valor és inferior al de l'element més petit d'aquesta col·lecció\" \n" +
                            "                        \"history : imprimeix les darreres 11 ordres (sense els seus arguments)\" \n" +
                            "                        \"filter_by_unit_of_measure unitOfMeasure : mostrar elements el valor del camp unitOfMeasure és igual al donat\" \n" +
                            "                        \"print_unique_part_number partNumber : imprimiu valors únics per al camp partNumber\" \n" +
                            "                        \"print_field_descending_owner owner : imprimeix els valors del camp de propietari per ordre descendent\\n"},
            };

    public Object[][] getContents() {
        return contents;
    }
}
package src.resources;

import java.util.ListResourceBundle;

public class resource_nor extends ListResourceBundle {
    private static final Object[][] contents =
            {
                    {"loginLabel", "Logg Inn"},
                    {"passwordLabel", "Passord"},
                    {"registerButton", "Registrering"},
                    {"loginButton", "Inngang"},

                    //COMMON
                    {"name", "Produktnavn"},
                    {"coordinateX", "Koordinere X"},
                    {"coordinateY", "Koordinere Y"},
                    {"price", "Pris"},
                    {"creationDate", "Dato for opprettelse"},
                    {"partNumber", "Beløp"},
                    {"unitOfMeasure", "Enhet"},
                    {"personName", "Eierens navn"},
                    {"personHeight", "Eierhøyde"},
                    {"personEyeColor", "Eierens øyenfarge"},
                    {"locationName", "Stedsnavn"},
                    {"locationX", "X beliggenhet"},
                    {"locationY", "Y beliggenhet"},
                    {"locationZ", "Z beliggenhet"},
                    {"creator", "Skaperen"},

                    {"PCS", "TING"},
                    {"MILLILITERS", "MILLILITER"},
                    {"GRAMS", "GRAMS"},

                    {"TING", "PCS"},
                    {"MILLILITER", "MILLILITERS"},
                    {"GRAMS", "GRAMS"},

                    {"RED", "RØD"},
                    {"BLUE", "BLÅ"},
                    {"ORANGE", "ORANSJE"},
                    {"WHITE", "HVIT"},
                    {"BROWN", "BRUN"},

                    {"RØD", "RED"},
                    {"BLÅ", "BLUE"},
                    {"ORANSJE", "ORANGE"},
                    {"HVIT", "WHITE"},
                    {"BRUN", "BROWN"},

                    {"applyButton", "Å akseptere"},

                    //Path Window
                    {"openButton", "åpen oppdagelsesreisende"},
                    {"pathLabel", "Legg inn filsti"},

                    //ADD WINDOW
                    {"addRadioButton",
                            "Legg til"},
                    {"addIfMaxRadioButton", "Legg til hvis maks"},
                    {"addIfMinRadioButton", "Legg til hvis minimum"},
                    {"cancelButton",
                            "Avbryt"},

                    // WORKSPACE
                    {"logoutButton", "Logg av"},
                    {"addButton", "Legg til vare"},
                    {"infoButton", "Informasjon"},
                    {"removeButton", "Slett element"},
                    {"historyButton", "Historie"},
                    {"executeScriptButton", "Kjør manus"},
                    {"clearButton", "Klar Samling"},
                    {"helpButton", "Hjelp"},
                    {"collectionTab", "Samling"},
                    {"visualizationTab", "Visualisering"},
                    {"sortItem", "Sortere"},
                    {"reverseSortItem", "Sorter i omvendt rekkefølge"},
                    {"undoItem", "Gå tilbake til gammel tilstand"},
                    {"filterItem", "Filter"},
                    {"deleteItem", "Slett"},
                    {"usernameLabel", "Bruker:"},
                    {"languageButton", "Skifte språk"},

                    {"russianRadioButton", "Русский"},
                    {"norwegianRadioButton", "Norsk"},
                    {"panamanianRadioButton", "Panameño"},
                    {"catalanRadioButton", "Català"},

                    {"A new script was started to execute\n", "Manuset ble lansert vellykket!\n"},
                    {"The result of filtering by unit of measure:\n", "Dimensjonelt filtreringsresultat:\n"},
                    {"The history of your last used commands:\n", "Historikk om tidligere brukte kommandoer:\n"},
                    {"Type can not be defined because collection is empty!\n", "Samlingstypen kan ikke bestemmes fordi samlingen er tom.\n"},
                    {"Problem with general class. Can not find type of class!", "Det var et problem med hovedklassen. Kan ikke finne klassetype!\n"},
                    {"Element was successfully removed.\n", "Elementet er slettet.\n"},
                    {"You don't have a permission to change this element!\n", "Du har ikke autorisasjon til å endre dette produktet!\n"},
                    {"The element with this id wasn't found.\n", "En vare med denne IDen ble ikke funnet!\n"},
                    {"The element's id was successfully updated!\n", "Elementet har blitt oppdatert!\n"},
                    {"Some problems with adding a product.", "Har du problemer med å legge til et produkt!\n"},
                    {"Product was successfully added to the collection.\n", "Produktet har blitt lagt til!\n"},
                    {"There are some problems with adding a product to collection.\n", "Har du problemer med å legge til et produkt!\n"},
                    {"You are trying to add the product which isn't a min!\n", "Produktet er ikke den laveste prisen!\n"},
                    {"You are trying to add the product which isn't a max!\n", "Produktet er ikke maksimal pris!\n"},


                    {"helpText", "info : vis informasjon om samlingen (type, initialiseringsdato, antall elementer osv.) i standard utstrøm\" \n" +
                            "                        \"show : sende til standardutgangsstrømmen alle elementene i samlingen i en strengrepresentasjon\" \n" +
                            "                        \"add {element} : legg til en ny vare i samlingen\" \n" +
                            "                        \"update id {element} : oppdater verdien av et samleelement hvis ID er lik det spesifiserte\" \n" +
                            "                        \"remove_by_id id : fjerne et element fra samlingen etter ID-en\" \n" +
                            "                        \"clear : tydelig samling\" \n" +
                            "                        \"save : lagre samling til fil\" \n" +
                            "                        \"execute_script file_name : lese og kjør skriptet fra den spesifiserte filen. Skriptet inneholder kommandoer i samme form som brukeren interaktivt angir\" \n" +
                            "                        \"exit : avslutt programmet (uten å lagre i en fil)\" \n" +
                            "                        \"add_if_max {element} : legg til et nytt element i samlingen hvis verdien overstiger verdien av det største elementet i denne samlingen\" \n" +
                            "                        \"add_if_min {element} : legg til et nytt element i samlingen hvis verdien er mindre enn for det minste elementet i denne samlingen\" \n" +
                            "                        \"history : skriv ut de siste 11 kommandoene (uten deres argumenter)\" \n" +
                            "                        \"filter_by_unit_of_measure unitOfMeasure : vis elementer hvis verdi for enhetOfMeasure er lik den gitte\" \n" +
                            "                        \"print_unique_part_number partNumber : skriv ut unike verdier for partNumber-feltet\" \n" +
                            "                        \"print_field_descending_owner owner : skriv ut eierfeltverdiene i synkende rekkefølge\\n"},
            };

    public Object[][] getContents() {
        return contents;
    }
}

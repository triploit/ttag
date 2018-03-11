package io.github.triploit.ttag;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Runtime implements Serializable
{
    public static Game game = new Game();
    private static List<String> error_names = new ArrayList<>(); // Alle fehlerhaften Namen, die beim Laden aufgetreten sind
    private static List<String> error_messages = new ArrayList<>(); // Alle Errors, die beim Laden aufgetreten sind
    private static List<Integer> error_ids = new ArrayList<>(); // Alle fehlerhaften/nicht gefundenen Namen, die beim Laden aufgetreten sind
    private static List<String> error_ids_messages = new ArrayList<>(); // Alle generierten Nachrichten mit den fehlerhaften ID's zur Ausgabe
    public static String yaml_file; // Die Angegebene YAML-Datei
    public static boolean warning = true; // Die "warning: true/false" Option in der YAML-Datei
    public static int warning_count = 0; // Anzahl der Warnungen

    public static int setInt(Object o, String _warning) // Einen Integerwert beim Laden setzen, siehe "Room.java" oder "Door.java"
    {
        if (o == null && ((String) o) == null)
        {
            if (_warning != null)
            {
                System.out.println("warnung "+(warning_count+1)+": "+_warning);
                Runtime.warning_count++;
            }
            return 0;
        }


        int ret = 0;

        try
        {
            ret = Integer.parseInt((String) o);
        }
        catch (Exception ex)
        {
            if (_warning != null) System.out.println("AUF WARNUNG: "+_warning+"\n\t    -> \""+(String) o+"\": konnte trotz angabe nicht gesetzt werden, muss eine zahl sein!");
            else System.out.println("error: konnte ein attribut nich setzen. leider keine weiteren hinweise verfuegbar.");
            System.exit(1);
        }

        return ret;
    }

    public static String setString(Object o, String _warning) // Einen String beim Laden setzen, siehe "Room.java" oder "Door.java"
    {
        if (o == null && ((String) o) == null)
        {
            if (_warning != null && warning)
            {
                System.out.println("warnung "+(warning_count+1)+": "+_warning);
                Runtime.warning_count++;
            }
            return "<NOT SET>";
        }
        return (String) o;
    }



    public static List<Integer> setListINT(Object o, String _warning) // Eine Liste setzen, siehe "Room.java" oder "Door.java" oder "Item.java"
    {
        if (o == null && ((List<Integer>) o) == null)
        {
            if (_warning != null && warning)
            {
                System.out.println("warnung "+(warning_count+1)+": "+_warning);
                Runtime.warning_count++;
            }
            return new ArrayList<>();
        }
        return (List<Integer>) o;
    }

    public static List<String> setListSTR(Object o, String _warning) // Eine Liste setzen
    {
        if (o == null && ((List<String>) o) == null)
        {
            if (_warning != null && warning)
            {
                System.out.println("warnung "+(warning_count+1)+": "+_warning);
                Runtime.warning_count++;
            }
            return new ArrayList<>();
        }
        return (List<String>) o;
    }

    public static void printWarning(String s) // Eine Warnung ausgeben
    {
        if (Runtime.warning)
        {
            System.out.println("warnung "+(warning_count+1)+": "+s);
            warning_count++;
        }
    }

    public static boolean setBool(Object o, String _warning) // Einen Boolean setzen, siehe "Room.java" oder "Door.java"
    {
        if (o == null || ((String) o).equals("<NOT SET>"))
        {
            if (_warning != null && warning)
            {
                System.out.println("warnung "+(warning_count+1)+": "+_warning);
                Runtime.warning_count++;
            }
            return false;
        }

        boolean ret = false;

        try
        {
            if (((String) o).equalsIgnoreCase("true")) ret = true;
        }
        catch (Exception ex)
        {
            if (_warning != null) System.out.println("AUF WARNUNG: "+_warning+"\n\t    -> \""+(String) o+"\": konnte trotz angabe nicht gesetzt werden, muss \"true\" oder \"false\" sein!");
            else System.out.println("error: konnte ein attribut nich setzen. leider keine weiteren hinweise verfuegbar.");
            System.exit(1);
        }

        return ret;
    }

    public static void save(Game to_save, String name) // Spiel speichern
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(to_save);
            oos.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static Game load(String save_file) // Spiel laden
    {
        Game g = null;

        try
        {
            FileInputStream fis = new FileInputStream(save_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            g = (Game) ois.readObject();
            ois.close();
        }
        catch (EOFException ex)
        {
            return null;
        }
        catch (IOException e)
        {
            System.out.println("error: speicherdatei nicht gefunden.");
            return null;
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("error: fehler im code oder in der speicherdatei.");
            return null;
        }

        return g;
    }

    public static void initYaml(String file) // Neues Spiel aus YAML laden
    {
        try
        {
            YamlReader reader = new YamlReader(new FileReader(file));
            Runtime.game = new Game();

            while (true)
            {
                Map D = (Map) reader.read();
                if (D == null) break;

                if (D.get("warnings") != null && ((String) D.get("warnings")) != null)
                    Runtime.warning = Boolean.parseBoolean((String) D.get("warnings"));

                try
                {
                    Runtime.game.name = (String) D.get("name");
                }
                catch (Exception ex) {
                    System.out.println("error: konnte den namen des spiels nicht abrufen!");
                    System.exit(1);
                }

                Item.init(D); // Alle Items aus der YAML laden
                Room.init(D); // Alle Räume ...
                // Monster.init(D); // Alle Monster ...
                Door.init(D); // Alle Türen ...

                for (int i = 0; i < error_names.size(); i++)
                {
                    if (game.nameIsDefined(error_names.get(i)))
                        continue;
                    else
                    {
                        System.out.println(error_messages.get(i));
                        System.exit(1);
                    }
                }

                for (int i = 0; i < error_ids.size(); i++)
                {
                    if (game.idIsDefined(error_ids.get(i)))
                        continue;
                    else
                    {
                        System.out.println(error_ids_messages.get(i));
                        System.exit(1);
                    }
                }

                try
                {
                    Runtime.game.player.inventory_size = Integer.parseInt((String) ((Map) D.get("player")).get("inventory")); // Größe des Spielerinventars setzen. Vielleich später mit "Stärketränken" o.ä. Verknüofbar
                    Runtime.game.player.position = Integer.parseInt((String) ((Map) D.get("player")).get("position")); // Anfangsraum des Spielers, wenn das Spiel geladen wird

                }
                catch (Exception ex) {
                    System.out.println("error: konnte den spieler nicht abrufen!");
                    System.exit(1);
                }

            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (YamlException e)
        {
            e.printStackTrace();
        }
    }

    public static void addInitErrorNames(String name, String errormessage) // Neuen gefundenen Error zu Liste hinzufügen
    {
        error_names.add(name);
        error_messages.add(errormessage);
    }

    public static void addInitErrorIDs(int id, String errormessage) // Neuen gefundenen Error (mit einer ID) zur Liste hinzufügen
    {
        error_ids.add(id);
        error_ids_messages.add(errormessage);
    }
}

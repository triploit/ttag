package io.github.triploit.ttag;

import java.io.Serializable;
import java.util.*;

public class Commands implements Serializable
{
    private static String[] ignore = {"dessen", "dessem", "ihrem", "ihren", "denen", "dem", "in", "ins", "dich", "mich", "mein", "sein"};
    private static String[] artikel = {"den", "der", "jenen", "jener", "die", "jene", "jene", "das", "jenes"};
    private static String[] pronomen = {"er", "sie", "es", "ihn"};
    private static String object = "";

    public static List<String> lexer(String input)
    {
        if (input.length() == 0)
        {
            System.out.println("Also du musst schon was eingeben!");
            return null;
        }

        Integer[] found = null;
        List<Integer[]> founds = new ArrayList<>();
        String input2 = input;

        found = (Runtime.game.findNameIn(input2));

        while (found != null)
        {
            founds.add(found);
            input2 = input2.substring(0, found[0]) + input2.substring(found[1] + 1, input2.length() - 1);
            found = (Runtime.game.findNameIn(input2));
        }

        String tmp = "";
        List<String> tokens = new ArrayList<>();

        for (int i = 0; i < input.length(); i++)
        {
            if (founds.size() != 0)
            {

                for (Integer[] f : founds)
                {
                    int count = 0;

                    if (i == f[0])
                    {
                        while (count < f[1])
                        {
                            tmp += "" + input.charAt(i);
                            count++;
                            i++;

                            if (i >= input.length()) break;
                        }

                        if (i >= input.length()) break;
                        tokens.add(tmp);
                        tmp = "";
                    }
                }
            }

            if (i >= input.length()) break;

            if (input.charAt(i) == ' ')
            {
                if (!tmp.equals("") && tmp.length() > 0)
                    tokens.add(tmp);
                tmp = "";
            }
            else
                tmp += "" + input.charAt(i);
        }

        if (!tmp.equals(""))
            tokens.add(tmp);

        for (int i = 0; i < tokens.size(); i++)
        {
            for (String s : ignore)
            {
                if (tokens.get(i).equalsIgnoreCase(s))
                {
                    tokens.remove(i);
                    i--;
                    break;
                }
            }
        }

        for (int i = 0; i < tokens.size(); i++)
        {
            if (tokens.get(i).equals("") || tokens.get(i).length() == 0)
                tokens.remove(i);
        }

        return tokens;
    }

    public static void execute(List<String> args)
    {
        List<String> s = args;

        // Artikel & Personalpronomen hinzufügen
        // sie = get(x+1) // (sie = "Fackel")
        // "leg sie weg".(replace sie -> Fackel)
        // Ignore Wörter drinne lassen, sind Artikel

        if (s.size() == 0)
        {
            System.out.println("Pardon?");
            return;
        }

        for (int i = 0; i < s.size(); i++)
        {
            for (String a : artikel)
            {
                if (a.equalsIgnoreCase(s.get(i)))
                {
                    object = s.get(i+1);
                }
            }
        }

        for (int i2 = 0; i2 < s.size(); i2++)
        {
            for (String a : pronomen)
            {
                if (a.equalsIgnoreCase(s.get(i2)))
                {
                    s.set(i2, object);
                }
            }
        }

        for (String str : s)
        {
            System.out.println("? \""+str+"\"");
        }

        for (int i = 0; i < s.size(); i++)
        {
            for (String str : ignore)
            {
                if (s.size() == 0) break;
                if (s.get(i).equalsIgnoreCase(str))
                {
                    s.remove(i);
                    break;
                }
            }

            for (String str : artikel)
            {
                if (s.size() == 0) break;
                if (s.get(i).equalsIgnoreCase(str))
                {
                    s.remove(i);
                    break;
                }
            }

            for (String str : pronomen)
            {
                if (s.size() == 0) break;
                if (s.get(i).equalsIgnoreCase(str))
                {
                    s.remove(i);
                    break;
                }
            }
        }

        boolean item_light = false;
        boolean f = false;

        for (Item it : Runtime.game.player.inventory.items)
        {
            if (it.attributes.light && it.attributes.activated)
            {
                f = true;
            }
        }

        Runtime.game.player.does_light = f;
        f = false;

        for (Item it : Runtime.game.getItemsAtPosition(Runtime.game.player.position))
        {
            if (it.attributes.light && it.attributes.activated)
            {
                f = true;
            }
        }

        item_light = f;

        /*
        * Liste von Befehlen:
        *   l               - sich umschauen
        *   help            - kleine Hilfe
        *   untersuche      - ein Objekt untersuchen
        *   lies            - etwas lesen
        *   leg weg         - etwas weglegen
        *   nimm            - etwas nehmen
        *   i               - schauen was man im inventar hat
        *   w/o/n/s         - richtung wechseln
        *   
        *   load            - spiel laden
        *   save            - spiel speichern
        *   new_game        - neuen spielstand
        * */

        if (s.get(0).equals("l") || s.get(0).equals("look"))
        {

            System.out.println("<===> " + Runtime.game.getRoomByID(Runtime.game.player.position).name.alternative + " <===>");

            if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
            {
                if (Runtime.game.getRoomByID(Runtime.game.player.position).see_dark.length() >= 1 && !Runtime.game.getRoomByID(Runtime.game.player.position).see_dark.equals("<NOT SET>")) System.out.println(Runtime.game.getRoomByID(Runtime.game.player.position).see_dark);
                else System.out.println("Es ist hier zu dunkel um etwas zu sehen.");
            }
            else
            {
                System.out.println(Runtime.game.getRoomByID(Runtime.game.player.position).see_light);
            }


            for (Item i : Runtime.game.getItemsAtPosition(Runtime.game.player.position))
            {
                if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
                {
                    if (i.see_dark.length() >= 1 && !i.see_dark.equals("<NOT SET>")) System.out.println(i.see_dark);
                }
                else
                {
                    System.out.println(i.see_light);
                }
            }
        }
        else if (s.get(0).equalsIgnoreCase("untersuche") || s.get(0).equalsIgnoreCase("untersuch"))
        {
            if (s.size() < 2)
            {
                System.out.println("Was willst du denn untersuchen?");
            }
            else
            {
                if (Runtime.game.existsItem(s.get(1)))
                {
                    Item i = Runtime.game.getItemByName(s.get(1));

                    if (!i.description.equals("<NOT SET>") && !i.description.isEmpty())
                    {
                        if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
                        {
                            System.out.println("Es ist hier zu dunkel um etwas zu erkennen.");
                        }
                        else
                        {
                            System.out.println(i.description);
                        }
                    }

                    if (!i.description.isEmpty() && !i.description.equals("<NOT SET>"))
                    {
                        if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
                        {
                            System.out.println("Es ist hier zu dunkel um etwas zu erkennen.");
                        }
                        else
                        {
                            System.out.println(i.description);
                        }
                    }
                    else
                        System.out.println("Das kann man nicht untersuchen.");
                }
                else if (Runtime.game.player.inventory.existsItemByName(s.get(1)))
                {
                    Item i = Runtime.game.player.inventory.getItemByName(s.get(1));

                    if (!i.description.isEmpty() && !i.description.equals("<NOT SET>"))
                    {
                        if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
                        {
                            System.out.println("Es ist hier zu dunkel um es zu untersuchen.");
                        }
                        else
                        {
                            System.out.println(i.description);
                        }
                    }
                    else
                        System.out.println("Das kann man nicht untersuchen. Schade, oder?");
                }
                else
                {
                    System.out.println("So etwas existiert hier nicht.");
                }
            }
        }
        else if (s.get(0).equals("lies") || s.get(0).equals("les") || s.get(0).equals("lese"))
        {
            if (s.size() < 2)
            {
                System.out.println("Ja... ähm... kannst du mir das nächste mal noch sagen was du lesen willst?");
            }
            else
            {
                if (Runtime.game.existsItem(s.get(1)))
                {
                    Item i = Runtime.game.getItemByName(s.get(1));

                    if (i.attributes.readable)
                    {
                        if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
                        {
                            System.out.println("Es ist hier zu dunkel um zu lesen.");
                        }
                        else
                        {
                            System.out.println(i.attributes.text);
                        }
                    }
                    else
                        System.out.println("Das kann man nicht lesen.");
                }
                else if (Runtime.game.player.inventory.existsItemByName(s.get(1)))
                {
                    Item i = Runtime.game.player.inventory.getItemByName(s.get(1));
                    if (i.attributes.readable)
                    {
                        if (Runtime.game.getRoomByID(Runtime.game.player.position).attributes.dark && !Runtime.game.player.does_light && !item_light)
                        {
                            System.out.println("Es ist hier zu dunkel um zu lesen.");
                        }
                        else
                        {
                            System.out.println(i.attributes.text);
                        }
                    }
                    else
                        System.out.println("Das kann man nicht lesen.");
                }
                else
                {
                    System.out.println("So ein Item existiert hier nicht.");
                }
            }
        }
        else if (s.get(0).equalsIgnoreCase("leg") && (s.get(s.size()-1).equalsIgnoreCase("weg") || (s.get(s.size()-2).equalsIgnoreCase("auf") && s.get(s.size()-1).equalsIgnoreCase("Boden")) || s.get(s.size()-1).equalsIgnoreCase("hin")))
        {
            if (s.size() < 3)
            {
                System.out.println("Sag mir das nächste mal doch bitte, was du weglegen willst?");
            }
            else
            {
                if (Runtime.game.player.inventory.existsItemByName(s.get(1)))
                {
                    Runtime.game.addItemFromPlayer(Runtime.game.player.inventory.getItemByName(s.get(1)).id);
                    System.out.println("Ok.");
                }
                else
                {
                    System.out.println("So ein Item besitzt du nicht.");
                }
            }
        }
        else if (s.get(0).equalsIgnoreCase("nimm"))
        {
            if (s.size() < 2)
            {
                System.out.println("Kannst du mir das nächste mal noch sagen, was du nehmen willst?");
            }
            else
            {
                if (Runtime.game.existsItem(s.get(1)))
                {
                    Runtime.game.player.inventory.collectItemFromGame(Runtime.game.getItemByName(s.get(1)).id);
                    System.out.println("Ok.");
                }
                else
                {
                    System.out.println("So ein Item existiert hier nicht.");
                }
            }
        }
        else if (s.get(0).equalsIgnoreCase("w"))
        {
            if (Runtime.game.getRoomByID(Runtime.game.player.position).west != -1 && !Runtime.game.isDirectionClosed(Runtime.game.getRoomByID(Runtime.game.player.position).door, "west"))
            {
                Runtime.game.player.position = Runtime.game.getRoomByID(Runtime.game.player.position).west;
                System.out.println("=> " + Runtime.game.getRoomByID(Runtime.game.player.position).name.alternative);
            }
            else
            {
                String msg = Runtime.game.getDirectionDescription(Runtime.game.getRoomByID(Runtime.game.player.position).door, "west");

                if (msg == null)
                {
                    System.out.println("Dieser Weg ist nicht passierbar.");
                }
                else System.out.println(msg);
            }
        }
        else if (s.get(0).equalsIgnoreCase("o"))
        {
            if (Runtime.game.getRoomByID(Runtime.game.player.position).east != -1 && !Runtime.game.isDirectionClosed(Runtime.game.getRoomByID(Runtime.game.player.position).door, "east"))
            {
                Runtime.game.player.position = Runtime.game.getRoomByID(Runtime.game.player.position).east;
                System.out.println("=> " + Runtime.game.getRoomByID(Runtime.game.player.position).name.alternative);
            }
            else
            {
                String msg = Runtime.game.getDirectionDescription(Runtime.game.getRoomByID(Runtime.game.player.position).door, "east");

                if (msg == null)
                {
                    System.out.println("Dieser Weg ist nicht passierbar.");
                }
                else System.out.println(msg);
            }

        }
        else if (s.get(0).equalsIgnoreCase("n"))
        {
            if (Runtime.game.getRoomByID(Runtime.game.player.position).north != -1 && !Runtime.game.isDirectionClosed(Runtime.game.getRoomByID(Runtime.game.player.position).door, "north"))
            {
                Runtime.game.player.position = Runtime.game.getRoomByID(Runtime.game.player.position).north;
                System.out.println("=> " + Runtime.game.getRoomByID(Runtime.game.player.position).name.alternative);
            }
            else
            {
                String msg = Runtime.game.getDirectionDescription(Runtime.game.getRoomByID(Runtime.game.player.position).door, "north");

                if (msg == null)
                {
                    System.out.println("Dieser Weg ist nicht passierbar.");
                }
                else System.out.println(msg);
            }
        }
        else if (s.get(0).equalsIgnoreCase("s"))
        {
            if (Runtime.game.getRoomByID(Runtime.game.player.position).south != -1 && !Runtime.game.isDirectionClosed(Runtime.game.getRoomByID(Runtime.game.player.position).door, "south"))
            {
                Runtime.game.player.position = Runtime.game.getRoomByID(Runtime.game.player.position).south;
                System.out.println("=> " + Runtime.game.getRoomByID(Runtime.game.player.position).name.alternative);
            }
            else
            {
                String msg = Runtime.game.getDirectionDescription(Runtime.game.getRoomByID(Runtime.game.player.position).door, "south");

                if (msg == null)
                {
                    System.out.println("Dieser Weg ist nicht passierbar.");
                }
                else System.out.println(msg);
            }
        }
        else if (s.get(0).equalsIgnoreCase("i"))
        {
            System.out.println("In deinem Inventar ist folgendes: ");

            for (Item i : Runtime.game.player.inventory.items)
            {
                System.out.println("\t- "+i.name.alternative);
            }
            System.out.println("\nDu hast "+Runtime.game.player.inventory.getFilledSize()+"/"+Runtime.game.player.inventory_size+" freien Platz belegt.");
        }
        else if (s.get(0).equalsIgnoreCase("load"))
        {
            System.out.print("moechten sie den jetzigen spielstand speichern? (ja/nein) >> ");
            String inp = (new Scanner(System.in)).nextLine();

            if (inp.toLowerCase().equals("ja"))
                execute(lexer("save"));

            System.out.print("welche datei möchtest du laden? >> ");
            inp = (new Scanner(System.in)).nextLine();

            Runtime.game = new Game();
            Runtime.initYaml(Runtime.yaml_file);
            Runtime.game = Runtime.load(inp);
            System.out.println("Geladen!");

        }
        else if (s.get(0).equalsIgnoreCase("save"))
        {
            System.out.print("wie soll die speicherdatei heissen? >> ");
            String inp = (new Scanner(System.in)).nextLine();

            if (inp.isEmpty()) execute(lexer("save"));

            Runtime.save(Runtime.game, inp);
        }
        else if (s.get(0).equalsIgnoreCase("new_game"))
        {
            System.out.print("moechten sie den jetzigen spielstand speichern? (ja/nein) >> ");
            String inp = (new Scanner(System.in)).nextLine();

            if (inp.toLowerCase().equals("ja"))
                execute(lexer("save"));
            else if (!inp.toLowerCase().equals("nein"))
            {
                execute(lexer("new_game"));
                return;
            }

            Runtime.game = new Game();
            Runtime.initYaml(Runtime.yaml_file);
        }
        else if (s.get(0).equalsIgnoreCase("help") || s.get(0).equalsIgnoreCase("hilfe"))
        {
            System.out.println("Befehle, die du verwenden kannst:\n" +
                    "\n" +
                    "\thelp     - Zeigt diese Hilfe an.\n" +
                    "\tsave     - Speichert das aktuelle Spiel.\n" +
                    "\tload     - Neue Datei laden.\n" +
                    "\tnew_game - Faengt ein neues Spiel an.\n" +
                    "\ts        - Bewegt den Spieler Richtung Sueden.\n" +
                    "\tn        - Bewegt den Spieler Richtung Norden.\n" +
                    "\to        - Bewegt den Spieler Richtung Osten." +
                    "\tw        - Was koennte das wohl machen?\n" +
                    "\tl        - Sich umschauen. (l = look)\n" +
                    "\ti        - Zeigt an, was du alles im Inventar hast.\n"+
                    "\nAlle weiteren Befehle musst du selbst erraten!\n");
        }
        else if (s.get(0).equalsIgnoreCase("q") || s.get(0).equalsIgnoreCase("ende"))
        {
            System.out.print("moechten sie wirklich beenden? (ja/nein) >> ");
            String inp = (new Scanner(System.in)).nextLine();

            if (inp.equals("ja"))
            {
                System.out.print("spiel speichern? (ja/nein) >> ");
                inp = (new Scanner(System.in)).nextLine();

                if (inp.equals("ja"))
                {
                    execute(lexer("save"));
                    System.exit(0);
                }

                if (inp.equals("nein"))
                {
                    System.exit(0);
                }
            }
            else if (inp.equals("nein"))
            {
                return;
            }

            execute(lexer("q"));
        }
        else
        {
            System.out.println("Pardon?");
            return;
        }

        System.out.println("");
    }
}

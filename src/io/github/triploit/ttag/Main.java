package io.github.triploit.ttag;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main implements Serializable
{
    public static void main(String[] args)
    {
        if (args.length <= 1)
        {
            System.out.println("error: bitte gib mir ein spiel und eine speicherdatei (wenn vorhanden) zum starten!\n\nBeispiel:\n\t> ttag spiel.yml save.yml");
        }

        String file = args[0];
        String save_file = null;
        Runtime.yaml_file = file;

        if (args.length == 2)
        {
            save_file = args[1];
            Runtime.game = Runtime.load(save_file);

            if (Runtime.game == null)
            {
                System.out.println("error: konnte spiel nicht aus datei laden.\ninfo: initialisiere neues spiel...");
                Runtime.initYaml(file);
            }
        }
        else
        {
            Scanner s = new Scanner(System.in);
            String inp = "";

            while (!inp.equalsIgnoreCase("ja") && !inp.equalsIgnoreCase("nein"))
            {
                System.out.print("du hast keine speicherdatei angegeben.\nhast du eine da? (ja/nein) >> ");
                inp = s.nextLine();
            }

            if (inp.equalsIgnoreCase("nein")) Runtime.initYaml(file);
            else
            {
                System.out.print("wie heisst die datei? >> ");
                inp = s.nextLine();
                File f = new File(inp);

                if (f.exists())
                {
                    Runtime.game = Runtime.load(inp);

                    if (Runtime.game == null)
                    {
                        System.out.println("error: konnte spiel nicht aus datei laden.\ninfo: initialisiere neues spiel...");
                        Runtime.initYaml(file);
                    }
                }
                else
                {
                    System.out.println("error: die datei existiert nicht.");
                    System.exit(1);
                }
            }
        }

        if (Runtime.warning_count > 0)
        {
            System.out.println("info: das spiel enthaelt "+Runtime.warning_count+" warnungen.\ninfo: tipp an entwickler: nicht nur den namen des spiels koennen sie einstellen, sondern auch die warnungen ausschalten (true = an/false = aus):\n\tname: \""+Runtime.game.name+"\"\n\twarnings: false");
        }

        while (true)
        {
            System.out.print(">> ");
            String input;

            Scanner s = new Scanner(System.in);
            input = s.nextLine();

            List<String> output = Commands.lexer(input);

            if (output == null) continue;
            Commands.execute(output);
        }
    }
}

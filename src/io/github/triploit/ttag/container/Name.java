package io.github.triploit.ttag.container;

import java.io.Serializable;

public class Name implements Serializable
{
    public String light; // Name der angezeigt wird, wenn es hell ist
    public String dark; // Name der angezeigt wird, wenn es dunkel ist
    public String alternative; // Alternativer Name

    public String genitiv;
    public String akkusativ;
    public String dativ;
    public String name;
    public String genus; // Genus - Maskulin (m), Feminin (f), Neutrum (n) - Noch nicht benutzt

    public boolean equalsName(String name) // Schauen ob der Name einem String enspricht
    {
        if (name.equals(light) || name.equals(dark) || name.equals(alternative) || name.equals(genitiv) || name.equals(akkusativ) || name.equals(dativ) || name.equals(this.name))
        {
            return true;
        }

        return false;
    }
}

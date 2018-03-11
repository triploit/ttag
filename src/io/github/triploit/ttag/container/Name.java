package io.github.triploit.ttag.container;

import java.io.Serializable;

public class Name implements Serializable
{
    public String light;
    public String dark;
    public String alternative;

    public String genitiv;
    public String akkusativ;
    public String dativ;
    public String name;
    public String genus;

    public boolean equalsName(String name)
    {
        if (name.equals(light) || name.equals(dark) || name.equals(alternative) || name.equals(genitiv) || name.equals(akkusativ) || name.equals(dativ) || name.equals(this.name))
        {
            return true;
        }

        return false;
    }
}

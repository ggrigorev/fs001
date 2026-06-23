/*

package:gg.vivado.boards.board

<component_mode>
  <simplex>
    <display_name presence="REQUIRED" value="CDATA" />
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <description array="description" />
    <interfaces array="interfaces" />
    <preferred_ips array="preferred_ips" />
  </complex>
</component_mode>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:ComponentMode = extends Schema
    String:display_name
    String:name
    ArrayList<Description>:description = new ArrayList<>()
    ArrayList<Interfaces>:interfaces = new ArrayList<>()
    ArrayList<PreferredIps>:preferred_ips = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class ComponentMode extends Schema {

public String display_name = null;
public String name = null;
public ArrayList<Description> description = new ArrayList<>();
public ArrayList<Interfaces> interfaces = new ArrayList<>();
public ArrayList<PreferredIps> preferred_ips = new ArrayList<>();

}


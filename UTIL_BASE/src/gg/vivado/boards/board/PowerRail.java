/*

package:gg.vivado.boards.board

<power_rail>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
    <is_direct DEFAULT="false" ENUMERATION="true:false" />
    <num_phases presence="IMPLIED" value="CDATA" />
  </simplex>
  <complex>
    <supply array="supply" />
    <rail array="rail" />
  </complex>
</power_rail>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:PowerRail = extends Schema
    String:name
    String:is_direct
    String:num_phases
    ArrayList<Supply>:supply = new ArrayList<>()
    ArrayList<Rail>:rail = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class PowerRail extends Schema {

public String name = null;
public String is_direct = null;
public String num_phases = null;
public ArrayList<Supply> supply = new ArrayList<>();
public ArrayList<Rail> rail = new ArrayList<>();

}


/*

package:gg.vivado.boards.board

<data_property_group>
  <simplex>
    <name presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <data_property array="data_property" />
    <data_property_group array="data_property_group" />
  </complex>
</data_property_group>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:DataPropertyGroup = extends Schema
    String:name
    ArrayList<DataProperty>:data_property = new ArrayList<>()
    ArrayList<DataPropertyGroup>:data_property_group = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class DataPropertyGroup extends Schema {

public String name = null;
public ArrayList<DataProperty> data_property = new ArrayList<>();
public ArrayList<DataPropertyGroup> data_property_group = new ArrayList<>();

}


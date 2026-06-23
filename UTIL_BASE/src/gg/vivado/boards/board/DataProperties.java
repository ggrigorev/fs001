/*

package:gg.vivado.boards.board

<data_properties>
  <complex>
    <data_property_group array="data_property_group" />
  </complex>
</data_properties>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:DataProperties = extends Schema
    ArrayList<DataPropertyGroup>:data_property_group = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class DataProperties extends Schema {

public ArrayList<DataPropertyGroup> data_property_group = new ArrayList<>();

}


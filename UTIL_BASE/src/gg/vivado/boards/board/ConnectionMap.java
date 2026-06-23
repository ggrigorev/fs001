/*

package:gg.vivado.boards.board

<connection_map>
  <simplex>
    <c1_end_index presence="REQUIRED" value="NMTOKEN" />
    <c1_st_index presence="REQUIRED" value="NMTOKEN" />
    <c2_end_index presence="REQUIRED" value="NMTOKEN" />
    <c2_st_index presence="REQUIRED" value="NMTOKEN" />
    <component2 presence="IMPLIED" value="NMTOKEN" />
    <max_delay presence="IMPLIED" value="NMTOKEN" />
    <min_delay presence="IMPLIED" value="NMTOKEN" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <typical_delay DEFAULT="5" presence="FIXED" value="NMTOKEN" />
  </simplex>
  <complex>
    <enablement_dependencies array="enablement_dependencies" />
    <nets array="nets" />
  </complex>
</connection_map>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:ConnectionMap = extends Schema
    String:c1_end_index
    String:c1_st_index
    String:c2_end_index
    String:c2_st_index
    String:component2
    String:max_delay
    String:min_delay
    String:name
    String:typical_delay
    ArrayList<EnablementDependencies>:enablement_dependencies = new ArrayList<>()
    ArrayList<Nets>:nets = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class ConnectionMap extends Schema {

public String c1_end_index = null;
public String c1_st_index = null;
public String c2_end_index = null;
public String c2_st_index = null;
public String component2 = null;
public String max_delay = null;
public String min_delay = null;
public String name = null;
public String typical_delay = null;
public ArrayList<EnablementDependencies> enablement_dependencies = new ArrayList<>();
public ArrayList<Nets> nets = new ArrayList<>();

}


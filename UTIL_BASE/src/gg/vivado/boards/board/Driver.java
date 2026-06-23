/*

package:gg.vivado.boards.board

<driver>
  <simplex>
    <os presence="REQUIRED" value="CDATA" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <platform presence="REQUIRED" value="CDATA" />
    <pre_compiled presence="REQUIRED" value="NMTOKEN" />
    <processor_architecture presence="REQUIRED" value="NMTOKEN" />
    <vendor presence="REQUIRED" value="NMTOKEN" />
    <version presence="REQUIRED" value="NMTOKEN" />
  </simplex>
  <complex>
    <description />
    <driver_files />
  </complex>
</driver>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:Driver = extends Schema
    String:os
    String:name
    String:platform
    String:pre_compiled
    String:processor_architecture
    String:vendor
    String:version
    Description:description
    DriverFiles:driver_files

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class Driver extends Schema {

public String os = null;
public String name = null;
public String platform = null;
public String pre_compiled = null;
public String processor_architecture = null;
public String vendor = null;
public String version = null;
public Description description = null;
public DriverFiles driver_files = null;

}


/*

package:gg.vivado.boards.board

<interface>
  <simplex>
    <mode presence="IMPLIED" ENUMERATION="master:slave" />
    <name presence="REQUIRED" value="NMTOKEN" />
    <of_component presence="IMPLIED" value="NMTOKEN" />
    <optional presence="IMPLIED" ENUMERATION="true" />
    <order presence="IMPLIED" value="NMTOKEN" />
    <preset_proc presence="IMPLIED" value="NMTOKEN" />
    <type presence="IMPLIED" value="NMTOKEN" />
  </simplex>
  <complex>
    <description array="description" />
    <enablement_dependencies array="enablement_dependencies" />
    <parameters array="parameters" />
    <port_maps array="port_maps" />
    <preferred_ips array="preferred_ips" />
  </complex>
</interface>

    import:java.util = ArrayList
    import:gg.base.xml.sch
    import:static gg.vivado.boards.board.L_Board
    import:static gg.vivado.boards.board.U_Board
    class:XInterface = extends Schema
    String:mode
    String:name
    String:of_component
    String:optional
    String:order
    String:preset_proc
    String:type
    ArrayList<Description>:description = new ArrayList<>()
    ArrayList<EnablementDependencies>:enablement_dependencies = new ArrayList<>()
    ArrayList<Parameters>:parameters = new ArrayList<>()
    ArrayList<PortMaps>:port_maps = new ArrayList<>()
    ArrayList<PreferredIps>:preferred_ips = new ArrayList<>()

*/

package gg.vivado.boards.board;

import java.util.ArrayList;
import gg.base.xml.sch.*;
import static gg.vivado.boards.board.L_Board.*;
import static gg.vivado.boards.board.U_Board.*;

public class XInterface extends Schema {

public String mode = null;
public String name = null;
public String of_component = null;
public String optional = null;
public String order = null;
public String preset_proc = null;
public String type = null;
public ArrayList<Description> description = new ArrayList<>();
public ArrayList<EnablementDependencies> enablement_dependencies = new ArrayList<>();
public ArrayList<Parameters> parameters = new ArrayList<>();
public ArrayList<PortMaps> port_maps = new ArrayList<>();
public ArrayList<PreferredIps> preferred_ips = new ArrayList<>();

}


package ro.utcluj.bpel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * This class is used to create {@link BPELProcess} instances.
 * 
 * @author Florin Pop
 *
 */

public class BPELProcessFactory {
	
	/**
	 * 
	 * Creates a {@link BPELProcess} instance from a BPEL file.
	 * 
	 * @param path the path of the BPEL file.
	 * @return the generated BPEL process instance.
	 * 
	 * @throws FileNotFoundException if the BPEL file doesn't exist.
	 */
	public static BPELProcess fromFile(String path) throws FileNotFoundException {
		
		XStream xs = new XStream();
		xs.alias("bpel:process", BPELProcess.class);
		xs.alias("bpel:sequence", BPELSequence.class);
		xs.alias("bpel:flow", BPELFlow.class);
		xs.alias("bpel:invoke", BPELInvoke.class);
		xs.alias("bpel:if", BPELIf.class);
		xs.alias("bpel:elseif", BPELElseIf.class);
		xs.alias("bpel:else", BPELElse.class);
		
		xs.useAttributeFor(BPELActivity.class, "name");
		xs.useAttributeFor(BPELCondition.class, "probability");
		
		xs.omitField(BPELProcess.class, "bpel:import");
		xs.omitField(BPELProcess.class, "bpel:partnerLinks");
		xs.omitField(BPELProcess.class, "bpel:variables");
		xs.omitField(BPELProcess.class, "bpel:receive");
		xs.omitField(BPELSequence.class, "bpel:receive");
		
		xs.addImplicitCollection(BPELActivity.class, "activities");
		
		
		return (BPELProcess) xs.fromXML(new FileInputStream(path));	
	}
}

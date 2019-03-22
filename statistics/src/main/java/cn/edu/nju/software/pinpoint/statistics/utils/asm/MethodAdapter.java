package cn.edu.nju.software.pinpoint.statistics.utils.asm;

import java.util.HashMap;

import cn.edu.nju.software.pinpoint.statistics.entity.StaticCallInfo;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

public class MethodAdapter extends MethodVisitor implements Opcodes {

    protected String className = null;
    protected int access = -1;
    protected String name = null;
    protected String desc = null;
    protected String signature = null;
    protected String[] exceptions = null;

    public static HashMap<String, StaticCallInfo> classEdges = new HashMap<String, StaticCallInfo>();

    public static HashMap<String, StaticCallInfo> methodEdges = new HashMap<String, StaticCallInfo>();


    public MethodAdapter(final MethodVisitor mv, final String className, final int access, final String name,
                         final String desc, final String signature, final String[] exceptions) {
        // super(ASM5, mv, access, name, desc);
        super(ASM6, mv);
        this.className = className;
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;

    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
//        System.out.println(this.className + " " + this.name + "   call  " + owner + " " + name);
        if (mv != null) {
            mv.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        if (!owner.equals(this.className)) {

            String edgeKey = this.className + "_!_" + owner;
            StaticCallInfo myedge = classEdges.get(edgeKey);
            if (myedge != null) {
                int weight = myedge.getCount();
                myedge.setCount(weight + 1);
                classEdges.put(edgeKey, myedge);
            } else {
                StaticCallInfo newedge = new StaticCallInfo();
                newedge.setCaller(this.className.replace("/", ".")
                        .replace(";", ",")
                        .replace("(L", "(")
                        .replace(",)", ")")
                        .replace(")L",")")
                        .replace(",L", ","));
                newedge.setCallee(owner.replace("/", ".")
                        .replace(";", ",")
                        .replace("(L", "(")
                        .replace(",)", ")")
                        .replace(")L",")")
                        .replace(",L", ","));
                newedge.setCount(1);
                classEdges.put(edgeKey, newedge);
            }
        }

        String sourceMethodName = (this.className + "--!--" + this.name +"-!-"+ this.desc).replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(")L",")")
                .replace(",L", ",");
        int index1 = sourceMethodName.lastIndexOf(")");
        sourceMethodName = sourceMethodName.substring(0, index1 + 1);

//        String targetMethodName = owner+"."+name+desc;
        String targetMethodName = (owner + "--!--" + name +"-!-"+ desc).replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(")L",")")
                .replace(",L", ",");
        int index2 = targetMethodName.lastIndexOf(")");
        targetMethodName = targetMethodName.substring(0, index2 + 1);

        if (!sourceMethodName.equals(targetMethodName)) {
            String edgeKey = sourceMethodName + "_!_" + targetMethodName;

            StaticCallInfo myedge = methodEdges.get(edgeKey);
            if (myedge != null) {
                int weight = myedge.getCount();
                myedge.setCount(weight + 1);
                methodEdges.put(edgeKey, myedge);
            } else {
                StaticCallInfo newedge = new StaticCallInfo();
                newedge.setCaller(sourceMethodName);
                newedge.setCallee(targetMethodName);
                newedge.setCount(1);
                methodEdges.put(edgeKey, newedge);
            }

        }
    }
}
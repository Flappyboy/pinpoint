package cn.edu.nju.software.pinpoint.statistics.utils.asm;

import cn.edu.nju.software.pinpoint.statistics.entity.ClassNode;
import cn.edu.nju.software.pinpoint.statistics.entity.MethodNode;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

import java.util.HashMap;

public class ClassAdapter extends ClassVisitor implements Opcodes {

    private String owner;
    private boolean isInterface;

    public static HashMap<String, ClassNode> classNodes = new HashMap<String, ClassNode>();
    public static HashMap<String, MethodNode> methodNodes = new HashMap<String, MethodNode>();
    public static int interfaceNum = 0;

    public ClassAdapter() {
        super(ASM6);
    }

    // 该方法是当扫描类时第一个拜访的方法，主要用于类声明使用：visit( 类版本 ,修饰符 , 类名 , 泛型信息 , 继承的父类 , 实现的接口)
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (cv != null) {
            cv.visit(version, access, name, signature, superName, interfaces);

        }
        owner = name;
        String classname = owner
                .replace("/", ".");
//                .replace(";", ",")
//                .replace("(L", "(")
//                .replace(",)", ")")
//                .replace(",L", ",");
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
//        System.out.println(name);
        ClassNode cnode = classNodes.get(classname);
        if (cnode == null) {
            ClassNode cnode1 = new ClassNode();
            cnode1.setName(classname);
            if (isInterface) {
                cnode1.setType(1);
                interfaceNum ++;
            }else
                cnode1.setType(0);
            classNodes.put(classname, cnode1);
        }
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!isInterface) {
            mv = new MethodAdapter(mv, owner, access, name, desc, signature, exceptions);
        }

        String methodFullName = (this.owner + "." + name + desc)
                .replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(",L", ",")
                .replace(")L",")");
//        int index = methodName.lastIndexOf(")");
//        methodName = methodName.substring(0, index + 1);

//        String className = methodName.substring(0, index + 1);
        String className = this.owner.replace("/", ".");
        String methodName =(name+ desc)
                .replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(",L", ",")
                .replace(")L",")");
        MethodNode methodNode = new MethodNode();
        methodNode.setName(name);
        methodNode.setClassname(className);
        methodNode.setFullname(methodFullName);
//        methodNode.setClassid(className);
        methodNodes.put(methodFullName, methodNode);
        return mv;
    }

    public static void main(String[] args){
        String a = "Classnode.setId(java.(lang.Integer)";
        System.out.println(a.indexOf("("));
    }
}
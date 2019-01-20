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

    public ClassAdapter() {
        super(ASM6);
    }

    // 该方法是当扫描类时第一个拜访的方法，主要用于类声明使用：visit( 类版本 ,修饰符 , 类名 , 泛型信息 , 继承的父类 , 实现的接口)
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        if (cv != null) {
            cv.visit(version, access, name, signature, superName, interfaces);

        }
        owner = name
                .replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(",L", ",");;
        isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        System.out.println(name);
        ClassNode cnode = classNodes.get(name);
        if (cnode == null) {
            ClassNode cnode1 = new ClassNode();
            cnode1.setName(name);
            if (isInterface)
                cnode1.setType(1);
            else
                cnode1.setType(0);
            classNodes.put(name, cnode1);
        }
    }

    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
                                     final String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (!isInterface) {
            mv = new MethodAdapter(mv, owner, access, name, desc, signature, exceptions);
        }

        String methodName = (this.owner + "." + name + desc)
                .replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(",L", ",");
        int index = methodName.lastIndexOf(")");
        methodName = methodName.substring(0, index + 1);

        int index2 = methodName.lastIndexOf(".");
        String className = methodName.substring(0, index + 1);
        MethodNode sourceMethodNode = new MethodNode();
        String ename = (name + desc).replace("/", ".")
                .replace(";", ",")
                .replace("(L", "(")
                .replace(",)", ")")
                .replace(",L", ",");
        sourceMethodNode.setName(methodName);
        sourceMethodNode.setClassname(this.owner);
        sourceMethodNode.setClassid(className);
        methodNodes.put(methodName, sourceMethodNode);
        return mv;
    }
}
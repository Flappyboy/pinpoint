package cn.edu.nju.software.pinpoint.statistics.service.impl;

import cn.edu.nju.software.pinpoint.statistics.dao.AppMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.ClassNodeMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.MethodNodeMapper;
import cn.edu.nju.software.pinpoint.statistics.dao.StaticCallInfoMapper;
import cn.edu.nju.software.pinpoint.statistics.entity.*;
import cn.edu.nju.software.pinpoint.statistics.service.ClassNodeService;
import cn.edu.nju.software.pinpoint.statistics.service.MethodNodeService;
import cn.edu.nju.software.pinpoint.statistics.service.StaticCallService;
import cn.edu.nju.software.pinpoint.statistics.utils.FileUtil;
import cn.edu.nju.software.pinpoint.statistics.utils.asm.ClassAdapter;
import cn.edu.nju.software.pinpoint.statistics.utils.asm.MethodAdapter;
import cn.edu.nju.software.pinpoint.statistics.utils.file.FileCompress;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.asm.ClassReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

@Service
public class StaticCallServiceImpl implements StaticCallService {

    @Autowired
    private ClassNodeMapper classNodeMapper;
    @Autowired
    private MethodNodeMapper methodNodeMapper;
    @Autowired
    private StaticCallInfoMapper staticCallInfoMapper;
    @Autowired
    private ClassNodeService classNodeService;
    @Autowired
    private MethodNodeService methodNodeService;
    @Autowired
    private AppMapper appMapper;
    @Autowired
    private Sid sid;

    @Override
    @Async
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveStaticAnalysis(String appid, String compressFile,Integer flag) throws Exception {
        ArrayList<String> myfiles = new ArrayList<String>();
        String path = "";

        if(flag != null) {
            if(flag == 1) {
                String outPath = compressFile.trim().substring(0, compressFile.trim().lastIndexOf("."));
                System.out.println("解压路径：" + outPath);
                FileCompress.unCompress(compressFile, outPath);
                if (path.trim().endsWith(".war"))
                    path = outPath + "/WEB-INF/classes";
                else
                    path = outPath;
            }else
                path = compressFile;
        }else
            path = compressFile;
        FileUtil.traverseFolder(path, myfiles);
        System.out.println("class文件数：" + myfiles.size());
        for (String file : myfiles) {
            if (file.endsWith(".class")) {
                InputStream inputstream = new FileInputStream(new File(file));
                ClassReader cr = new ClassReader(inputstream);
                ClassAdapter ca = new ClassAdapter();
                cr.accept(ca, ClassReader.EXPAND_FRAMES);
            }
        }
        System.out.println("++++++++++++++");
        System.out.println(ClassAdapter.methodNodes.size());
        System.out.println(MethodAdapter.methodEdges.size());
        System.out.println("++++++++++++++");

        //方法
        HashMap<String, MethodNode> methodnoedes = ClassAdapter.methodNodes;
        HashMap<String, StaticCallInfo> methodedges = MethodAdapter.methodEdges;
        //类
        HashMap<String, ClassNode> classNodes = ClassAdapter.classNodes;
        HashMap<String, StaticCallInfo> classEdges = MethodAdapter.classEdges;



        System.out.println("保存类结点");
        saveClassNode(classNodes, appid);
        System.out.println("保存类边");
        saveClassEdge(classEdges, appid);
        System.out.println("保存方法结点");
        saveMethodNode(methodnoedes, appid);
        System.out.println("保存方法边");
        saveMethodEdge(methodedges, appid);

        App app = new App();
        app.setId(appid);
        app.setStatus(1);
        app.setClasscount(classNodes.size());
        app.setFunctioncount(methodnoedes.size());
        app.setInterfacecount(ClassAdapter.interfaceNum);
        appMapper.updateByPrimaryKeySelective(app);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束！");
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<HashMap<String, String>> findEdgeByAppId(String appid, int page, int pageSize, int type) {
        List<HashMap<String, String>> edges = new ArrayList<>();
        PageHelper.startPage(page, pageSize);
        StaticCallInfoExample example = new StaticCallInfoExample();
        StaticCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAppidEqualTo(appid).andFlagEqualTo(1).andTypeEqualTo(type);
        example.setOrderByClause("count");
        List<StaticCallInfo> scinfos = staticCallInfoMapper.selectByExample(example);

        for (StaticCallInfo scinfo : scinfos) {
            HashMap<String, String> edge = new HashMap<String, String>();
            if (type == 0) {
                ClassNode callerNode = classNodeService.findById(scinfo.getCaller());
                ClassNode calleeNode = classNodeService.findById(scinfo.getCallee());
                edge.put("caller", callerNode.getName());
                edge.put("callee", calleeNode.getName());
                edge.put("count", String.valueOf(scinfo.getCount()));
            }
            if (type == 1) {
                MethodNode callerNode = methodNodeService.findById(scinfo.getCaller());
                MethodNode calleeNode = methodNodeService.findById(scinfo.getCallee());
                edge.put("caller", callerNode.getName());
                edge.put("callee", calleeNode.getName());
                edge.put("count", String.valueOf(scinfo.getCount()));
            }
            edges.add(edge);
        }
        return edges;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int countOfStaticAnalysis(String appid, int type) {
        StaticCallInfoExample example = new StaticCallInfoExample();
        StaticCallInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAppidEqualTo(appid).andFlagEqualTo(1).andTypeEqualTo(type);
        return staticCallInfoMapper.countByExample(example);
    }

    private void saveMethodNode(HashMap<String, MethodNode> methodnoedes, String appid) {
        int methodKey = 0;
        for (Map.Entry<String, MethodNode> entry : methodnoedes.entrySet()) {
            MethodNode methodNode = entry.getValue();
//            System.out.println(methodNode);
            MethodNodeExample example = new MethodNodeExample();
            MethodNodeExample.Criteria criteria = example.createCriteria();
            criteria.andFullNameEqualTo(methodNode.getFullname())
//                    .andNameEqualTo(methodNode.getName())
//                    .andClassnameEqualTo(methodNode.getClassname())
                    .andAppidEqualTo(appid).andFlagEqualTo(1);
//            criteria.andNameEqualTo(methodNode.getName()).andClassnameEqualTo(methodNode.getClassname())
//                    .andAppidEqualTo(appid).andFlagEqualTo(1);
//            List<MethodNode> mnodes = methodNodeMapper.selectByExample(example);
            List<MethodNode> mnodes = methodNodeMapper.selectByExampleWithBLOBs(example);

            methodKey += 1;

            if (mnodes.size() == 0 || mnodes == null) {
                String id = sid.nextShort();

                String classId = "";
                ClassNodeExample classexample = new ClassNodeExample();
                ClassNodeExample.Criteria classcriteria = classexample.createCriteria();
                classcriteria.andNameEqualTo(methodNode.getClassname()).andAppidEqualTo(appid).andFlagEqualTo(1);
                List<ClassNode> cnodes = classNodeMapper.selectByExample(classexample);
                if (cnodes.size() != 0 && cnodes != null) {
                    ClassNode cn = cnodes.get(0);
                    classId = cn.getId();
                }

                methodNode.setId(id);
                methodNode.setClassid(classId);
                methodNode.setAppid(appid);
                methodNode.setFlag(1);
                methodNode.setKey(methodKey);
                methodNode.setCreatedat(new Date());
                methodNode.setUpdatedat(new Date());
                methodNodeMapper.insert(methodNode);
            } else {
                MethodNode mn = mnodes.get(0);
                mn.setKey(methodKey);
                methodNodeMapper.updateByPrimaryKeySelective(mn);
//                MethodNodeExample example1 = new MethodNodeExample();
//                MethodNodeExample.Criteria criteria1 = example1.createCriteria();
//                criteria1.andIdEqualTo(mn.getId()).andFlagEqualTo(1);
//                methodNodeMapper.updateByExampleSelective(mn, example1);
            }
        }

    }

    private void saveMethodEdge(HashMap<String, StaticCallInfo> methodedges, String appid) {
        for (Map.Entry<String, StaticCallInfo> entry : methodedges.entrySet()) {
            String sourceid = "";
            String targetid = "";
            StaticCallInfo methodEdge = entry.getValue();

//            System.out.println("getSource   :    " + methodEdge.getCaller());
            String sCallerName = methodEdge.getCaller();
//            String[] scallerArr = sCallerName.split("-!-");
            MethodNodeExample example1 = new MethodNodeExample();
            MethodNodeExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andFullNameEqualTo(sCallerName.replace("-!-","")).andAppidEqualTo(appid).andFlagEqualTo(1);
//            criteria1.andNameEqualTo(scallerArr[1]).andClassnameEqualTo(scallerArr[0]).andAppidEqualTo(appid).andFlagEqualTo(1);
            List<MethodNode> sourceNodes = methodNodeMapper.selectByExampleWithBLOBs(example1);
            if (!sourceNodes.isEmpty()) {
                MethodNode mynode = sourceNodes.get(0);
                sourceid = mynode.getId();
            }

//            System.out.println("getTarget   :   " + methodEdge.getCallee());
            String tCalleeName = methodEdge.getCallee();
//            String[] tcalleeArr = tCalleeName.split("-!-");
            MethodNodeExample example2 = new MethodNodeExample();
            MethodNodeExample.Criteria criteria2 = example2.createCriteria();
            criteria1.andFullNameEqualTo(tCalleeName.replace("-!-","")).andAppidEqualTo(appid).andFlagEqualTo(1);
//            criteria2.andNameEqualTo(tcalleeArr[1]).andClassnameEqualTo(tcalleeArr[0]).andAppidEqualTo(appid).andFlagEqualTo(1);
            List<MethodNode> targetNodes = methodNodeMapper.selectByExampleWithBLOBs(example2);
            if (!targetNodes.isEmpty()) {
                MethodNode mynode = targetNodes.get(0);
                targetid = mynode.getId();
            }

            if (sourceid != "" && targetid != "") {
                StaticCallInfoExample edgeExample = new StaticCallInfoExample();
                StaticCallInfoExample.Criteria edgeCriteria = edgeExample.createCriteria();
                edgeCriteria.andCallerEqualTo(sourceid)
                        .andCalleeEqualTo(targetid)
                        .andAppidEqualTo(appid)
                        .andTypeEqualTo(1).andFlagEqualTo(1);
                List<StaticCallInfo> myedge = staticCallInfoMapper.selectByExample(edgeExample);
                if (myedge.size() == 0 || myedge == null) {
                    String id = sid.nextShort();
                    int count = methodEdge.getCount();
                    StaticCallInfo record = new StaticCallInfo();
                    record.setId(id);
                    record.setCaller(sourceid);
                    record.setCallee(targetid);
                    record.setCount(count);
                    record.setAppid(appid);
                    record.setCreatedat(new Date());
                    record.setUpdatedat(new Date());
                    record.setFlag(1);
                    record.setType(1);
                    staticCallInfoMapper.insert(record);
                }
            }

        }
    }

    private void saveClassNode(HashMap<String, ClassNode> classNodes, String appid) {
        int classKey = 0;

        for (Map.Entry<String, ClassNode> entry : classNodes.entrySet()) {
            ClassNode classNode = entry.getValue();

            ClassNodeExample example = new ClassNodeExample();
            ClassNodeExample.Criteria criteria = example.createCriteria();
            criteria.andNameEqualTo(classNode.getName()).andAppidEqualTo(appid).andFlagEqualTo(1);
            List<ClassNode> cnodes = classNodeMapper.selectByExample(example);

            classKey += 1;

            if (cnodes.size() == 0 || cnodes == null) {
                String id = sid.nextShort();
                classNode.setId(id);
                classNode.setAppid(appid);
                classNode.setFlag(1);
                classNode.setKey(classKey);
                classNode.setCreatedat(new Date());
                classNode.setUpdatedat(new Date());
                classNodeMapper.insert(classNode);
            } else {
                ClassNode cn = cnodes.get(0);
                cn.setKey(classKey);
                cn.setUpdatedat(new Date());
                ClassNodeExample example1 = new ClassNodeExample();
                ClassNodeExample.Criteria criteria1 = example1.createCriteria();
                criteria1.andIdEqualTo(cn.getId()).andFlagEqualTo(1);
                classNodeMapper.updateByExampleSelective(cn, example1);
            }
        }
    }

    private void saveClassEdge(HashMap<String, StaticCallInfo> classEdges, String appid) {
        for (Map.Entry<String, StaticCallInfo> entry : classEdges.entrySet()) {
            String sourceid = "";
            String targetid = "";
            StaticCallInfo classEdge = entry.getValue();

//            System.out.println("getSource   :    " + classEdge.getCaller());
            ClassNodeExample example1 = new ClassNodeExample();
            ClassNodeExample.Criteria criteria1 = example1.createCriteria();
            criteria1.andNameEqualTo(classEdge.getCaller()).andAppidEqualTo(appid).andFlagEqualTo(1);
            List<ClassNode> sourceNodes = classNodeMapper.selectByExample(example1);
            if (!sourceNodes.isEmpty()) {
                ClassNode mynode = sourceNodes.get(0);
                sourceid = mynode.getId();
            }

//            System.out.println("getTarget   :   " + classEdge.getCallee());
            ClassNodeExample example2 = new ClassNodeExample();
            ClassNodeExample.Criteria criteria2 = example2.createCriteria();
            criteria2.andNameEqualTo(classEdge.getCallee()).andAppidEqualTo(appid).andFlagEqualTo(1);
            List<ClassNode> targetNodes = classNodeMapper.selectByExample(example2);
            if (!targetNodes.isEmpty()) {
                ClassNode mynode = targetNodes.get(0);
                targetid = mynode.getId();
            }

            if (sourceid != "" && targetid != "") {
                StaticCallInfoExample edgeExample = new StaticCallInfoExample();
                StaticCallInfoExample.Criteria edgeCriteria = edgeExample.createCriteria();
                edgeCriteria.andCallerEqualTo(sourceid)
                        .andCalleeEqualTo(targetid)
                        .andAppidEqualTo(appid)
                        .andTypeEqualTo(0).andFlagEqualTo(1);
                List<StaticCallInfo> myedge = staticCallInfoMapper.selectByExample(edgeExample);
                if (myedge.size() == 0 || myedge == null) {
                    String id = sid.nextShort();
                    int count = classEdge.getCount();
                    StaticCallInfo record = new StaticCallInfo();
                    record.setId(id);
                    record.setCaller(sourceid);
                    record.setCallee(targetid);
                    record.setCount(count);
                    record.setAppid(appid);
                    record.setCreatedat(new Date());
                    record.setUpdatedat(new Date());
                    record.setFlag(1);
                    record.setType(0);
                    staticCallInfoMapper.insert(record);
                }
            }

        }
    }
}

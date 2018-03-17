package org.cros.blockchain.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.cros.blockchain.model.Component;
import org.cros.blockchain.model.ConModel;
import org.cros.blockchain.service.IIntelligentService;
import org.cros.blockchain.util.Pagination;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName:IntelligentContoller <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年5月9日 下午2:15:26 <br/>
 *
 * @author hokuny@foxmail.com
 * @version
 * @since JDK 1.6
 * @see
 */
@Controller
@RequestMapping(value = "/Intelligent")
public class IntelligentContoller {
    @Resource
    private IIntelligentService intelligentService;

    /**
     *
     * queryModel:查询模板详情 byId
     *
     * @Title queryModel
     * @retrun void
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteComponent", method = RequestMethod.POST)
    public void deleteComponent(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PrintWriter out = null;
        try {
            String component_id = request.getParameter("id");
            int b = intelligentService.deleteComponent(component_id);
            JSONObject json = new JSONObject();
            if (b > 0) {
                model.addAttribute("message", "删除组件成功");
                json.put("success", "删除组件成功");
            } else {
                model.addAttribute("message", "删除组件失败");
                json.put("success", "删除组件失败");
            }
            response.setCharacterEncoding("UTF-8"); // 设置编码格式
            response.setContentType("text/html"); // 设置数据格式
            out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }

    }

    /**
     *
     * getComponentsContent: 加载组件数据
     *
     * @Title startDyw
     * @retrun void
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getComponentsContent", method = RequestMethod.POST)
    public void getComponentsContent(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PrintWriter out = null;
        String assemblyName = ServletRequestUtils.getStringParameter(request, "assemblyName");
        String currPage = ServletRequestUtils.getStringParameter(request, "currPage");
        int currPageInt = 0;
        if (currPage != null) {
            currPageInt = Integer.parseInt(currPage);
        }
        JSONObject json = new JSONObject();
        ArrayList listJson = new ArrayList();
        Pagination page = intelligentService.queryComponents(assemblyName, currPageInt, 10);
        if (page != null) {
            List<Component> Components = page.getItems();
            for (Component c : Components) {
                Map map = new LinkedHashMap();
                listJson.add(map);
                map.put("id", c.getId());
                map.put("key", c.getKey());
                map.put("description", c.getDescription());
                map.put("comValue", c.getComValue());
                String s = c.getStatus();
                if ("1".equals(s)) {
                    s = "启用";
                } else {
                    s = "禁用";
                }
                map.put("status", s);
            }
        }
        json.put("datas", listJson);
        json.put("totl", page.getTotalNumber());
        json.put("totalPage", page.getTotalPage());
        json.put("currentPage", page.getCurrentPage());
        json.put("pageSize", page.getPageSize());
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     *
     * getComponentsContent: 加载模板数据
     *
     * @Title startDyw
     * @retrun void
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getAllModels", method = RequestMethod.POST)
    public void getModelsContent(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PrintWriter out = null;
        String contractualName = ServletRequestUtils.getStringParameter(request, "contractualName");
        String currPage = ServletRequestUtils.getStringParameter(request, "currPage");
        int currPageInt = 0;
        if (currPage != null) {
            currPageInt = Integer.parseInt(currPage);
        }
        JSONObject json = new JSONObject();
        ArrayList listJson = new ArrayList();
        Pagination page = intelligentService.queryModels(contractualName, currPageInt, 10);
        if (page != null) {
            List<Component> Components = page.getItems();
            for (Component c : Components) {
                Map map = new LinkedHashMap();
                listJson.add(map);
                map.put("id", c.getId());
                map.put("key", c.getKey());
                map.put("description", c.getDescription());
                map.put("comValue", c.getComValue());
                String s = c.getStatus();
                if ("1".equals(s)) {
                    s = "启用";
                } else {
                    s = "禁用";
                }
                map.put("status", s);
            }
        }
        json.put("datas", listJson);
        json.put("totl", page.getTotalNumber());
        json.put("totalPage", page.getTotalPage());
        json.put("currentPage", page.getCurrentPage());
        json.put("pageSize", page.getPageSize());
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     *
     * queryModel:查询模板详情 byId
     *
     * @Title queryModel
     * @retrun void
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/queryComponent", method = RequestMethod.POST)
    public void queryComponent(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        PrintWriter out = null;
        try {
            String component_id = request.getParameter("id");
            Component c = intelligentService.queryComponentById(component_id);
            JSONObject json = new JSONObject();
            json.put("id", c.getId());
            json.put("key", c.getKey());
            json.put("description", c.getDescription());
            json.put("comValue", c.getComValue());
            response.setCharacterEncoding("UTF-8"); // 设置编码格式
            response.setContentType("text/html"); // 设置数据格式
            out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     *
     * queryModel:查询模板详情 byId
     *
     * @Title queryModel
     * @retrun void
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/queryModel", method = RequestMethod.POST)
    public void queryModel(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = null;
        try {
            String conmodel_id = request.getParameter("id");
            ConModel c = intelligentService.queryModelById(conmodel_id);
            JSONObject json = new JSONObject();
            json.put("id", c.getId());
            json.put("key", c.getKey());
            json.put("description", c.getDescription());
            json.put("comValue", c.getComValue());
            response.setCharacterEncoding("UTF-8"); // 设置编码格式
            response.setContentType("text/html"); // 设置数据格式
            out = response.getWriter();
            out.write(json.toJSONString());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     *
     * queryPageForComponents:获取组件明细页面
     *
     * @Title queryPageForComponents
     * @retrun String
     */
    @RequestMapping(value = "/getComponents", method = { RequestMethod.GET })
    public String queryPageForComponents(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return "assemblyManager";
    }

    /**
     *
     * queryPageForModel:获取模型页面
     *
     * @Title queryPageForModel
     * @retrun String
     */
    @RequestMapping(value = "/getModels", method = { RequestMethod.GET })
    public String queryPageForModel(Model model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return "contractualTemplate";
    }

    /**
     *
     * saveModel:新增模板
     *
     * @Title saveModel
     * @retrun void
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/saveComponent", method = RequestMethod.POST)
    public void saveComponent(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = null;
        JSONObject jsonRe = new JSONObject();
        try {
            request.setCharacterEncoding("UTF-8");
            String list = request.getParameter("list");
            JSONObject json = (JSONObject) JSONObject.parse(list);
            String com_id = json.containsKey("com_id") == true ? json.getString("com_id") : "";
            String key = json.containsKey("key") == true ? json.getString("key") : "";
            String description = json.containsKey("description") == true
                    ? json.getString("description")
                    : "";
            String value = json.containsKey("comValue") == true ? json.getString("comValue") : "";
            String status = json.containsKey("status") == true ? json.getString("status") : "";

            Document doc = new Document("key", key).append("description", description)
                    .append("comValue", value).append("status", status);
            if ("".equals(com_id)) {
                intelligentService.saveComponent(doc);
                model.addAttribute("message", "添加组件成功");
                jsonRe.put("success", "添加组件成功");
            } else {
                Component c = new Component();
                c.setId(com_id);
                c.setKey(key);
                c.setDescription(description);
                c.setStatus(status);
                c.setComValue(value);
                int b = intelligentService.updateComponent(c);
                if (b > 0) {
                    model.addAttribute("message", "编辑组件成功");
                    jsonRe.put("success", "编辑组件成功");
                } else {
                    model.addAttribute("message", "编辑组件失败");
                    jsonRe.put("success", "编辑组件失败");
                }

            }
            String props = jsonRe.toJSONString();
            response.setCharacterEncoding("UTF-8"); // 设置编码格式
            response.setContentType("text/html"); // 设置数据格式
            out = response.getWriter();
            out.write(props);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     *
     * saveModel:新增模板
     *
     * @Title saveModel
     * @retrun void
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/saveModel", method = RequestMethod.POST)
    public void saveModel(Model model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        PrintWriter out = null;
        JSONObject jsonRe = new JSONObject();
        try {
            request.setCharacterEncoding("UTF-8");
            String list = request.getParameter("list");
            JSONObject json = (JSONObject) JSONObject.parse(list);
            String key = json.containsKey("key") == true ? json.getString("key") : "";
            String description = json.containsKey("description") == true
                    ? json.getString("description")
                    : "";
            String value = json.containsKey("comValue") == true ? json.getString("comValue") : "";
            String status = json.containsKey("status") == true ? json.getString("status") : "";

            Document doc = new Document("key", key).append("description", description)
                    .append("comValue", value).append("status", status);
            intelligentService.saveComponent(doc);
            model.addAttribute("message", "添加/修改合约模板成功");
            jsonRe.put("success", "添加/修改合约模板成功");

            String props = jsonRe.toJSONString();
            response.setCharacterEncoding("UTF-8"); // 设置编码格式
            response.setContentType("text/html"); // 设置数据格式
            out = response.getWriter();
            out.write(props);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }
}

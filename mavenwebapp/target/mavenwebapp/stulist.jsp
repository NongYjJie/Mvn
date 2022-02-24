<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="mytag" uri="http://www.mytag.org/jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title>学生页面</title>
  <script  src="${pageContext.request.contextPath}/js/jquery-3.6.0.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/date/My97DatePicker/WdatePicker.js"></script>
</head>
<script type="text/javascript">
  $(function(){
    //查询
    function page(pageNumb){
      var pageSize = $("#pageSize").val();
      $.ajax({
        url:"${pageContext.request.contextPath}/queryStu",
        dataType:"json",//预期的服务器响应的数据类型。
        type:"GET",
        data:{"pageSize":pageSize,"pageNumb":pageNumb},
        success:function(page){
          if(page.mark == "0"){
            alert("后台异常");
          }else{
            //先清空除了第一行的数据
            $("#testTable tr:not(:first)").remove();
            //设置分页参数
            $("#pageSize").val(page.pageSize);
            $("#pageNumb").val(page.pageNumb);
            $("#pageCount").val(page.pageCount);
            $("#sum").val(page.sum);

            //显示当前为第几页
            $("#whatpage").text(page.pageNumb);

            //显示总共有几页
            $("#pages").text(page.pageCount);

            //当前页为第一页时隐藏"上一页"
            if(page.pageNumb != 1){
              $("#uppage").show();
            }else{
              $("#uppage").hide();
            }
            //当前页为尾页时隐藏"下一页"
            if(page.pageNumb != page.pageCount){
              $("#nextpage").show();
            }else{
              $("#nextpage").hide();
            }

            var testTable = $("#testTable");
            var gender = "";
            for (var i = 0; i < page.list.length; i++) {
              var test = page.list[i];
              if(test.gender == 1){
                gender ="男";
              }else {
                gender ="女";
              }
              testTable.append("<tr><td>"+test.id+"</td><td>"
                      +test.stuName+"</td><td>"
                      +test.age+"</td><td>"
                      +gender+"</td><td>"
                      +test.stuClass+"</td><td>"
                      +test.school.schName+"</td><td><img alt='' width='60px' height='50px' src='download?filePath="+encodeURIComponent(test.showHead)+"'></td><td><a href='javascript:void(0)' getdelId='"+test.id+"' class='delId'>删除</a>&nbsp;&nbsp;<a href='javascript:void(0)'  getupdId='"+test.id+"' class='updId'>修改</a></td></tr>");
            }
            //控制栏
            action();
          }
//
        }
      });

    }

    //action操作 删除, 修改，因为数据是异步加载，导致事件加载完成时，上面的数据还没加载
    function action(){
      //修改
      $(".updId").click(function(){

        //回显，通过ID把数据查询出来在回显
        var id = $(this).attr("getupdId");

        $.ajax({
          url:"${pageContext.request.contextPath}/update/"+id,
          dataType:"json",
          type:"GET",
          success:function(res){
            if(res.mark != "0"){
              $("#updateDiv").show();

              $("#updid").val(res.id);
              $("#updStudentName").val(res.stuName);
              $("#updAge").val(res.age);
              $("#updClas").val(res.stuClass);

              //选中单选或复选框
              $("input[name='gender'][value = '"+res.gender+"']").prop("checked","checked");
              //下拉框回显
              $("#schNameUpdateId option[value='"+res.schId+"']").prop("selected",true);

              var path = "download?filePath="+encodeURIComponent(res.showHead);
              //回显头像名称
              $("#updateShowHead").attr("src",path);

            }
          }
        });
      });

      //删除
      $(".delId").click(function(){
        //javascript对象，jquery
        var id = $(this).attr("getdelId");

        var par = $(this).parent().parent();//parent() 方法返回被选元素的直接父元素。
        $.ajax({
          url:"${pageContext.request.contextPath}/delete/"+id,
          dataType:"json",//预期的服务器响应的数据类型。
          type:"POST",//规定请求的类型
          data:{"_method": "DELETE"},//规定要发送到服务器的数据
          success:function(res){ // success: 当请求成功时运行的函数。
            if(res.mark == "1"){
              par.remove(); //remove() 方法删除被选元素及其子元素。

              //删除数据时不够，应该跳到上一页
              var pageSize = $("#pageSize").val();
              var pageCount = $("#pageCount").val();
              var pageNumb = $("#pageNumb").val();
              var sum = $("#sum").val();

              if(pageNumb <= pageCount ){
                if(sum % pageSize == 1 && pageNumb!=1 ){
                  page(parseInt(pageNumb)-1);
                }else{
                  page(parseInt(pageNumb));
                }
              }

            }
          }
        });
      });
    }

    //修改隐藏 取消
    $("#updcaseltest").click(function(){
      $("#updateDiv").hide();//隐藏
    });

    //修改保存
    $("#updtest").click(function(){
      /*            var id = $("#updid").val();
                  var account = $("#updaccount").val();
                  var password = $("#updpassword").val();
                  var name = $("#updname").val();
                  var addr = $("#updaddr").val();
                  var birth = $("#updbirth").val();*/

      var formData = new FormData();
      formData.append('id', $("#updid").val());
      formData.append('stuName', $("#updStudentName").val());
      formData.append('age', $('#updAge').val());
      formData.append('stuClass', $('#updClas').val());
      formData.append('schId',$('#schNameUpdateId').val());
      formData.append('gender',$('input:radio:checked').val());

      var files = $("#upshowHead")[0].files[0];
      formData.append('file',files);

      $.ajax({
        url:"${pageContext.request.contextPath}/update",
        dataType:"json",
        type:"PUT",
        data:formData,
        contentType: false,
        processData: false,
        success:function(res){
          $("#updateDiv").hide();//隐藏
          //查找testTable 里的所有tr,在从tr里查出第一个td
          $("#testTable tr").find("td:eq(0)").each(function(){
            if($(this).text() == formData.id){
              $(this).parent().find("td:eq(1)").text(formData.stuName);//find() 方法返回被选元素的后代元素，一路向下直到最后一个后代
              $(this).parent().find("td:eq(2)").text(formData.age);
              $(this).parent().find("td:eq(3)").text(formData.gender);
              $(this).parent().find("td:eq(4)").text(formData.studentClass);
              $(this).parent().find("td:eq(5)").text(formData.schId);
              $(this).parent().find("td:eq(6)").text(formData.file);

            }
            //修改结束到当前页
            var pageNumb = $("#pageNumb").val();
            page(pageNumb);
          });
        }
      });
    });



    //显示新增
    $("#AddStudent").click(function(){
      $("#addDiv").show(); //显示
    });

    //隐藏新增,并且清空输入内容
    $("#caseltest").click(function(){
      $("#addStudentName").val("");//val() 方法获得输入字段的值：
      $("#addAge").val("");
      $("#addClas").val("");
      $("#schName").val("0");
      $('input[type=radio][name="gender"]:checked').prop("checked", false);
      $("#addDiv").hide();//隐藏
    });
    //新增保存
    $("#addtest").click(function(){
      /*     var account = $("#addaccount").val();
           var password = $("#addpassword").val();
           var name = $("#addname").val();
           var addr = $("#addaddr").val();
           var birth = $("#addbirth").val();
           var showHead = $("#showHead").val();*/
      var formData = new FormData();
      formData.append('stuName', $("#addStudentName").val());
      formData.append('age', $('#addAge').val());
      formData.append('stuClass', $('#addClas').val());
      formData.append('schId', $('#schName').val());
      formData.append('gender',$('input:radio:checked').val());
      var files = $("#showHead")[0].files[0];
      formData.append('file',files);
      $.ajax({
        url:"${pageContext.request.contextPath}/insterStu",
        dataType:"json",
        type:"POST",
        data:formData,
        processData: false,
        contentType: false,
        success:function(res){
          if(res.mark != "0"){//append() 方法在被选元素的结尾插入内容（仍然在该元素的内部）
            $("#testTable").append("<tr><td>"+res.mark+"</td><td>"
                    +formData.studentName+"</td><td>"
                    +formData.age+"</td><td>"
                    +formData.gender+"</td><td>"
                    +formData.studentClass+"</td><td>"
                    +formData.schId+"</td><td>"
                    +formData.showHead+"</td><a href='javascript:void(0)' getdelId='"+res.mark+"' class='delId'>删除</a>&nbsp;&nbsp;<a href='javascript:void(0)' getupdId='"+res.mark+"' class='updId'>修改</a></td></tr>");

            //添加成功到最后一页
            var pageSize = $("#pageSize").val();
            var sum = $("#sum").val();
            var sps = (parseInt(sum)+1)/pageSize;//得到总共有几页
            sps = Math.ceil(sps);//向上取整数
            if(sps >= parseInt(pageNumb)){
              page(parseInt(sps))
            }

            //隐藏新增,并且清空输入内容
            $("#caseltest").click();
          }
        }
      });

    });


    //分页

    //内容改变事件
    $("#DisPlayPages").change(function(){
      var pageSize = $(this).val();
      $("#pageSize").val(pageSize);

      var pageSize = $("#pageSize").val();
      var pageCount = $("#pageCount").val();
      var pageNumb = $("#pageNumb").val();
      var sum = $("#sum").val();

      var sps = sum/pageSize;//得到总共有几页
      sps = Math.ceil(sps);//向上取整数
      if(sps >= parseInt(pageNumb)){
        page(parseInt(pageNumb))
      }else{
        page(1);
      }
    });
    //首页
    $("#firstpage").click(function(){
      page(1);
    });
    //尾页
    $("#Lastpage").click(function(){
      page($("#pageCount").val());
    });
    //上一页
    $("#uppage").click(function(){
      var pageNumb = $("#pageNumb").val();
      page(parseInt(pageNumb)-1);
    });
    //下一页
    $("#nextpage").click(function(){
      var pageNumb = $("#pageNumb").val();
      page(parseInt(pageNumb)+1);
    });
    //页面加载完成进行分页方法调用
    var pageNumb = $("#pageNumb").val();
    page(pageNumb);
  });
</script>
<body>
<a href="schlist.jsp">点击执行学校操作</a>
<input type="button" id="AddStudent" value="新增" ><br>
<table border="1" style="width: 800px;" id="testTable" >
  <tr align="center">
    <td>编号</td>
    <td >学生名称</td>
    <td>学生年龄</td>
    <td>学生性别</td>
    <td>学生班级</td>
    <td>所属学校</td>
    <td>学生头像</td>
    <td>操控栏</td>
  </tr>
</table>
<div >
  当前为第<span id="whatpage" style="color: pink; "></span>页&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="#" id="firstpage">首页</a>&nbsp;
  <a href="#" id="uppage">上一页</a>&nbsp;
  <a href="#" id="nextpage">下一页</a>&nbsp;
  <a href="#" id="Lastpage">尾页</a>&nbsp;&nbsp;&nbsp;&nbsp;
  总共有<span id="pages" style="color: #ffc0c0; "></span>页&nbsp;&nbsp;

  每页显示
  <select id="DisPlayPages">
    <option value="3">3</option>
    <option value="5">5</option>
    <option value="10">10</option><!-- 下拉 -->
    <option value="20">20</option>
  </select>
  条数据&nbsp;
</div>

<!-- 分页参数 -->

<input type="hidden" id="pageSize" value="3" ><!-- 每页显示多少条数据 -->
<input type="hidden" id="pageNumb" value="1"><!--当前第几页 -->
<input type="hidden" id="pageCount" ><!--总页数 -->
<input type="hidden" id="sum" ><!-- 总数据 -->


<!-- 添加 -->
<div id="addDiv" style="display: none;"  >
  姓名：<input type="text" id="addStudentName" ><br>
  出生年月：<input type="text" id="addAge" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  class="Wdate"><br>
  性别:<input type="radio" name="gender" class="gender" value="1" id="s1"/>男
       <input type="radio" name="gender" class="gender" value="0" id="s2"/>女<br>

  班级：<input type="text" id="addClas"><br>
  选择学校：<mytag:select id="schName"/><br>
  添加头像:<input type="file" id="showHead" name="file"/><br>
  <input type="button" id="addtest" value="添加">
  <input type="button" id="caseltest" value="取消">

</div>

<!-- 修改 -->
<div id="updateDiv" style="display: none;"  >
  <input type="hidden" id="updid"><br>
  姓名：<input type="text" id="updStudentName"><br>
  出生年月：<input type="text" id="updAge" onClick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})"  class="Wdate"><br>
  性别:<input type="radio" name="gender" class="gender" value="1" id="s1" checked="checked"/>男&nbsp;
      <input type="radio" name="gender" class="gender" value="0" id="s2" checked="checked"/>女<br>
  班级：<input type="text" id="updClas"><br>
  选择学校：<mytag:select id="schNameUpdateId"/><br>

  <img alt='' width='60px' height='50px' src='' id="updateShowHead"><br>
  头像:<input type="file" id="upshowHead" name="file"/><br>


  <input type="button" id="updtest" value="保存">
  <input type="button" id="updcaseltest" value="取消">
</div>

</body>
</html>

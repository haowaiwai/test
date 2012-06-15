<?php
header("Content-Type: text/html; charset=gb2312");
$pagesize = 8;//每页显示条数
$len = 19;//一共显示的条数
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script src="../jquery-1.7.1.js" type="text/javascript"></script>
<title>★NAN MADE IT★</title>
<style type="text/css">
<!--
body {
	background-image: url(../img/index-bg2.jpg);
	background-repeat: repeat;
	margin: 0px;
}
A:link {
	font-family:Tahoma
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: none   
}   
A:visited {   
	font-family:Tahoma
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: none   
}   
A:active {   
	font-family:Tahoma
	 FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: none   
}   
A:hover {   
	font-family:Tahoma
	FONT-SIZE: 12px; COLOR: #ff0000; TEXT-DECORATION: none   
}
.STYLE2 {
	color: #999999;
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: 12px;
}
.STYLE3 {color: #666666; font-family: Georgia, "Times New Roman", Times, serif; font-size: 12px; }
.STYLE4 {color: #CCCCCC}
.STYLE5 {
	font-size: 12px;
	color: #CCCCCC;
}
.STYLE6 {
	font-size: 24px;
	font-family: "黑体";
	color: #CCCCCC;
}
.STYLE7 {
	font-family: Tahoma;
	color: #CCCCCC;
}
.STYLE9 {
	font-size: 12px;
    font-family: Tahoma;
    color: #CCCCCC;
}
.STYLE10 {color: #FFFFFF}
-->
</style>
</head>
<body>

<!--顶部开始-->
<?php include("../header.php"); ?>
<!--顶部结束-->

<table width="200" border="0" align="center" style="margin-top:-3px;"> 
  <tr>
    <td><img src="../img/lunbo.jpg" width="910" height="271" /></td>
  </tr>
</table>
<table width="910" border="0" align="center" style="margin-top:4px;">
  <tr>
    <td><img src="newproducts.png" width="910" height="46" /></td>
  </tr>
</table>
<?php
if(isset($_GET["page"]))
	$page = $_GET["page"];
if(empty($page))
	$page = 0;
else
	$page=$page-1;
$pagecount = ceil($len / $pagesize);
$start = $page*$pagesize;
if($start >= $len)
	$start = 0;
$end = $start + $pagesize;
if($end  > $len )
	$end = $len ;
?>
<table width="910" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="576" height="341" background="products-bg1.jpg">
	<table width="576" border="0" align="center">
		<?php
		$i = $start;
		for(;;)
		{
			$n = sprintf("%04d", $len - $i);
			echo "<tr>\n";
			echo "<td align='center'><p>&nbsp;</p>\n";
			echo "<p><a href='http://www.nanmadeit.com/products/products_xiangqing.php?id=".$n."'><img src='./t-list/products-t".$n.".jpg' width='220' height='269' border='0' /></a></p>\n";
			echo "<p>&nbsp;</p></td>";
			$i++;
            $n = sprintf("%04d", $len - $i);
			if($i<=$end-1)
			{
				echo "<td align='center'><p>&nbsp;</p>\n";
				echo "<p><a href='http://www.nanmadeit.com/products/products_xiangqing.php?id=".$n."'><img src='./t-list/products-t".$n.".jpg' width='220' height='269' border='0' /></a></p>\n";
				echo "<p>&nbsp;</p></td>";
				$i++;
			}
			echo "</tr>\n";
			if($i >= $end)
				break;
		}
		?>
		<tr>
		<td align="center" colspan="2">
			<span class="STYLE10">
			<?php
			for ($i=1; $i<=$pagecount; $i++)
			{
				if($i == $page + 1)
					echo "<font color='white'>".$i."</font>\n";
				else
					echo "<a href='http://www.nanmadeit.com/products/products_list.php?page=".$i."'>".$i."</a>\n";
			}
			?>
			</span>
		</td>
		</tr>
      </table>
    <p>&nbsp; </p>
	</td>
    <td width="355" background="products-bg2.jpg" valign="top"><p>&nbsp;</p>
    <table width="277" border="0" align="center">
      <tr>
        <td width="271" align="center"><p><a href="http://nanmadeit.taobao.com/"><img src="likegoumai.jpg" width="271" height="62" border="0" /></a></p>
          </td>
      </tr>
      <tr valign="top">
        <td><p class="STYLE4 STYLE6">NANMADEIT since 2012 </p>
          <p align="left" class="STYLE5 STYLE4 STYLE7">2009年NANMADEIT诞生于北京东城。以混不吝的劲头杀入京城潮圈。开篇之作“北京帮派”系列以其融合京味文化的独特设计而深受广大京城潮人欢迎。2012年NANMADEIT整装待发，于北京朝阳开启第一家实体店铺。并一举推出“复仇者联盟”，“北京制造”等多个系列Tee。敬请期待！<br />
            -------------------------------------------------------------------<br />
            NANMADEIT store          
          <p align="left" class="STYLE5 STYLE4 STYLE7">店址:北京市朝阳区兴隆西街二号世纪天乐-潮青汇 三层3058          
          <p align="left" class="STYLE5 STYLE4 STYLE7">電話:13811299051 &nbsp;&nbsp;18601328129
          <p align="left" class="STYLE5 STYLE4 STYLE7"><span class="STYLE9">官方邮箱:nanmadeit@gmail.com</span>
          
          <p align="left" class="STYLE5"><span class="STYLE9">官方網站:http://www.nanmadeit.com/</span></p>
          <p align="left" class="STYLE5"><span class="STYLE9">官方微博:<a href="http://weibo.com/nanmadeit" target="_blank">http://weibo.com/nanmadeit</a><br />
            </span><span class="STYLE9"><br />
              官方淘寶線上商店:<a href="http://nanmadeit.taobao.com" target="_blank">http://nanmadeit.taobao.com</a>
            </span></p>
          <p class="STYLE4">&nbsp;</p></td>
      </tr>
    </table></td>
  </tr>
</table>

<!--底部开始-->
<?php include("../footer.php"); ?>
<!--底部结束-->

</body>
</html>

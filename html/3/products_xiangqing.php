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
.STYLE2 {
	color: #999999;
	font-family: Georgia, "Times New Roman", Times, serif;
	font-size: 12px;
}
.STYLE3 {color: #666666; font-family: Georgia, "Times New Roman", Times, serif; font-size: 12px; }
.STYLE4 {color: #CCCCCC}
.STYLE5 {
	font-size: 12px;
        font-family: Tahoma;
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
<table width="910" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="576" height="341" background="products-bg1.jpg">
	<table width="576" border="0">
	<?php
	header("Content-Type: text/html; charset=gb2312");
	if(isset($_GET["id"]))
		$id = $_GET["id"];
	$dir = './2012_5/products-t'.$id;
	foreach(scandir($dir) as $single){
		if(($single != ".")&&($single != ".."))
		{
			echo "<tr>\n";
			echo "<td align='center'><p>&nbsp;</p>\n";
			echo "<p><img src='".$dir."/".$single."' width='500'/></p>\n";
			echo "</td>";
			echo "</tr>";
		}
	}
	$doc = new DOMDocument();
	$doc->load("1.xml");
	$xpath = new DOMXPath($doc);
	$query = "//item[@id = 't".$id."']";
	$entries = $xpath->query($query);
	$content = "暂无";
	$title = "暂无";
	$turl = "";
	foreach ($entries as $entry) {
		for($i=1; $i<$entry->childNodes->length; $i+=2) {
			$testNode = $entry->childNodes->item($i);
			if($testNode->nodeName == "introduce")
			{
				$content = $testNode->nodeValue;
			}
			else if($testNode->nodeName == "title")
			{
				$title = $testNode->nodeValue;
			}
			else
			{
				$turl = $testNode->nodeValue;
			}
		}
	}
	?>
    </table></td>
    <td width="355" background="products-bg2.jpg" valign="top"><p>&nbsp;</p>
    <table width="275" border="0" align="center">
      <tr>
        <td width="271" align="center"><p>
		<?php 
		if($turl == "")
		{
			$turl = "http://nanmadeit.taobao.com/";
		}
		echo "<a href='".$turl."'>";
		?>
		<img src="likegoumai.jpg" width="271" height="62" border="0" /></a></p></td>
      </tr>
      <tr valign="top">
        <td>
		<p class="STYLE6">
		<?php 
		  echo iconv("UTF-8","GBK",$title);
		?>
		</p>
          <p align="left" class="STYLE5">
		  <?php 
		  echo iconv("UTF-8","GBK",$content);
		  ?>
            <br/>--------------------------------------------<br />
            NAN MADE IT Shop<br />
            店址:朝阳路朝青汇三层<br />
            電話:18601328129</p>
          <p align="left" class="STYLE5"><span class="STYLE9">官方網站:http://www.nanmadeit.com/<br />
            官方微博:http://<br />
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

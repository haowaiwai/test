<?php
header("Content-Type: text/html; charset=gb2312");
$pagesize = 8;//ÿҳ��ʾ����
$len = 19;//һ����ʾ������
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<script src="../jquery-1.7.1.js" type="text/javascript"></script>
<title>��NAN MADE IT��</title>
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
	font-family: "����";
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

<!--������ʼ-->
<?php include("../header.php"); ?>
<!--��������-->

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
        <td><p class="STYLE4 STYLE6">NANMADEIT ALL TEE</p>
          <p align="left" class="STYLE5 STYLE4 STYLE7">�˷��Ƴ���&quot;CHN-51&quot;��TEE����2012�_ʼ<br />
            NAN MADE IT ���l���&quot;CASINO LIFE&quot;���}ϵ�ЮaƷ���OӋ���������&quot;CASINO LIFE&quot;���}�� ��&quot;ROBBING THE POOR TO FEED THE RICH(��ؚ����)&quot;������Z���K�������[���еĸ��˽�ɫ����С͵��������ķ���ԏጣ� �����Y�����x����ԍ���ĺڰ��棬��һ��O���S���Ҿ����ஔ����ķ�����Ʒ,ֵ�ü�����ζ!<br />
            -------------------------------------------------------------------<br />
            NAN MADE IT Shop<br />
            ��ַ:����·���������<br />
            �Ԓ:18601328129</p>
          <p align="left" class="STYLE5"><span class="STYLE9">�ٷ��Wվ:http://www.nanmadeit.com/<br />
            �ٷ�΢��:http://<br />
            �ٷ��Ԍ������̵�:&nbsp<a href="http://nanmadeit.taobao.com" target="_blank">http://nanmadeit.taobao.com</a>
			</span></p>          
          <p class="STYLE4">&nbsp;</p></td>
      </tr>
    </table></td>
  </tr>
</table>

<!--�ײ���ʼ-->
<?php include("../footer.php"); ?>
<!--�ײ�����-->

</body>
</html>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Task</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/employeeTaskView.css" />
</head>
<body>

<jsp:include page="_menu.jsp"></jsp:include>
<div style="display: block; text-align: center">
    <h3>Current Draft</h3>
    <h4>It is your pick</h4>

    <form method="post" style="display: inline-block" action="${pageContext.request.contextPath}/employeeTask">
       <input type="hidden" name="redirectId" value="${param.redirectId}"/>
        <table>
            <tr>
                <th>${activeDraft.players[0]}</th>
                <th>${activeDraft.players[1]}</th>
                <th>${activeDraft.players[2]}</th>
                <th>${activeDraft.players[3]}</th>
                <th>${activeDraft.players[4]}</th>
                <th>${activeDraft.players[5]}</th>
                <th>${activeDraft.players[6]}</th>
                <th>${activeDraft.players[7]}</th>
            </tr>
            <tr>
                <td><label for="pick1"></label><input type="text" id="pick1" name="pick1" size = "3 px" ${activeDraft.picks[0].locked ? 'disabled' : ''} value=${activeDraft.picks[0].pick}></td>
                <td><label for="pick2"></label><input type="text" id="pick2" name="pick2" size = "3 px" ${activeDraft.picks[1].locked ? 'disabled' : ''} value=${activeDraft.picks[1].pick}></td>
                <td><label for="pick3"></label><input type="text" id="pick3" name="pick3" size = "3 px" ${activeDraft.picks[2].locked ? 'disabled' : ''} value=${activeDraft.picks[2].pick}></td>
                <td><label for="pick4"></label><input type="text" id="pick4" name="pick4" size = "3 px" ${activeDraft.picks[3].locked ? 'disabled' : ''} value=${activeDraft.picks[3].pick}></td>
                <td><label for="pick5"></label><input type="text" id="pick5" name="pick5" size = "3 px" ${activeDraft.picks[4].locked ? 'disabled' : ''} value=${activeDraft.picks[4].pick}></td>
                <td><label for="pick6"></label><input type="text" id="pick6" name="pick6" size = "3 px" ${activeDraft.picks[5].locked ? 'disabled' : ''} value=${activeDraft.picks[5].pick}></td>
                <td><label for="pick7"></label><input type="text" id="pick7" name="pick7" size = "3 px" ${activeDraft.picks[6].locked ? 'disabled' : ''} value=${activeDraft.picks[6].pick}></td>
                <td><label for="pick8"></label><input type="text" id="pick8" name="pick8" size = "3 px" ${activeDraft.picks[7].locked ? 'disabled' : ''} value=${activeDraft.picks[7].pick}></td>
            </tr>
            <tr>
                <td><label for="pick16"></label><input type="text" id="pick16" name="pick16" size = "3 px" ${activeDraft.picks[15].locked ? 'disabled' : ''} value=${activeDraft.picks[15].pick}></td>
                <td><label for="pick15"></label><input type="text" id="pick15" name="pick15" size = "3 px" ${activeDraft.picks[14].locked ? 'disabled' : ''} value=${activeDraft.picks[14].pick}></td>
                <td><label for="pick14"></label><input type="text" id="pick14" name="pick14" size = "3 px" ${activeDraft.picks[13].locked ? 'disabled' : ''} value=${activeDraft.picks[13].pick}></td>
                <td><label for="pick13"></label><input type="text" id="pick13" name="pick13" size = "3 px" ${activeDraft.picks[12].locked ? 'disabled' : ''} value=${activeDraft.picks[12].pick}></td>
                <td><label for="pick12"></label><input type="text" id="pick12" name="pick12" size = "3 px" ${activeDraft.picks[11].locked ? 'disabled' : ''} value=${activeDraft.picks[11].pick}></td>
                <td><label for="pick11"></label><input type="text" id="pick11" name="pick11" size = "3 px" ${activeDraft.picks[10].locked ? 'disabled' : ''} value=${activeDraft.picks[10].pick}></td>
                <td><label for="pick10"></label><input type="text" id="pick10" name="pick10" size = "3 px" ${activeDraft.picks[9].locked ? 'disabled' : ''} value=${activeDraft.picks[9].pick}></td>
                <td><label for="pick9"></label><input type="text" id="pick9" name="pick9" size = "3 px" ${activeDraft.picks[8].locked ? 'disabled' : ''} value=${activeDraft.picks[8].pick}></td>
            </tr>
            <tr>
                <td><label for="pick17"></label><input type="text" id="pick17" name="pick17" size = "3 px" ${activeDraft.picks[16].locked ? 'disabled' : ''} value=${activeDraft.picks[16].pick}></td>
                <td><label for="pick18"></label><input type="text" id="pick18" name="pick18" size = "3 px" ${activeDraft.picks[17].locked ? 'disabled' : ''} value=${activeDraft.picks[17].pick}></td>
                <td><label for="pick19"></label><input type="text" id="pick19" name="pick19" size = "3 px" ${activeDraft.picks[18].locked ? 'disabled' : ''} value=${activeDraft.picks[18].pick}></td>
                <td><label for="pick20"></label><input type="text" id="pick20" name="pick20" size = "3 px" ${activeDraft.picks[19].locked ? 'disabled' : ''} value=${activeDraft.picks[19].pick}></td>
                <td><label for="pick21"></label><input type="text" id="pick21" name="pick21" size = "3 px" ${activeDraft.picks[20].locked ? 'disabled' : ''} value=${activeDraft.picks[20].pick}></td>
                <td><label for="pick22"></label><input type="text" id="pick22" name="pick22" size = "3 px" ${activeDraft.picks[21].locked ? 'disabled' : ''} value=${activeDraft.picks[21].pick}></td>
                <td><label for="pick23"></label><input type="text" id="pick23" name="pick23" size = "3 px" ${activeDraft.picks[22].locked ? 'disabled' : ''} value=${activeDraft.picks[22].pick}></td>
                <td><label for="pick24"></label><input type="text" id="pick24" name="pick24" size = "3 px" ${activeDraft.picks[23].locked ? 'disabled' : ''} value=${activeDraft.picks[23].pick}></td>
            </tr>
            <tr>
                <td><label for="pick32"></label><input type="text" id="pick32" name="pick32" size = "3 px" ${activeDraft.picks[31].locked ? 'disabled' : ''} value=${activeDraft.picks[31].pick}></td>
                <td><label for="pick31"></label><input type="text" id="pick31" name="pick31" size = "3 px" ${activeDraft.picks[30].locked ? 'disabled' : ''} value=${activeDraft.picks[30].pick}></td>
                <td><label for="pick30"></label><input type="text" id="pick30" name="pick30" size = "3 px" ${activeDraft.picks[29].locked ? 'disabled' : ''} value=${activeDraft.picks[29].pick}></td>
                <td><label for="pick29"></label><input type="text" id="pick29" name="pick29" size = "3 px" ${activeDraft.picks[28].locked ? 'disabled' : ''} value=${activeDraft.picks[28].pick}></td>
                <td><label for="pick28"></label><input type="text" id="pick28" name="pick28" size = "3 px" ${activeDraft.picks[27].locked ? 'disabled' : ''} value=${activeDraft.picks[27].pick}></td>
                <td><label for="pick27"></label><input type="text" id="pick27" name="pick27" size = "3 px" ${activeDraft.picks[26].locked ? 'disabled' : ''} value=${activeDraft.picks[26].pick}></td>
                <td><label for="pick26"></label><input type="text" id="pick26" name="pick26" size = "3 px" ${activeDraft.picks[25].locked ? 'disabled' : ''} value=${activeDraft.picks[25].pick}></td>
                <td><label for="pick25"></label><input type="text" id="pick25" name="pick25" size = "3 px" ${activeDraft.picks[24].locked ? 'disabled' : ''} value=${activeDraft.picks[24].pick}></td>
            </tr>
            <tr>
                <td><label for="pick33"></label><input type="text" id="pick33" name="pick33" size = "3 px" ${activeDraft.picks[32].locked ? 'disabled' : ''} value=${activeDraft.picks[32].pick}></td>
                <td><label for="pick34"></label><input type="text" id="pick34" name="pick34" size = "3 px" ${activeDraft.picks[33].locked ? 'disabled' : ''} value=${activeDraft.picks[33].pick}></td>
                <td><label for="pick35"></label><input type="text" id="pick35" name="pick35" size = "3 px" ${activeDraft.picks[34].locked ? 'disabled' : ''} value=${activeDraft.picks[34].pick}></td>
                <td><label for="pick36"></label><input type="text" id="pick36" name="pick36" size = "3 px" ${activeDraft.picks[35].locked ? 'disabled' : ''} value=${activeDraft.picks[35].pick}></td>
                <td><label for="pick37"></label><input type="text" id="pick37" name="pick37" size = "3 px" ${activeDraft.picks[36].locked ? 'disabled' : ''} value=${activeDraft.picks[36].pick}></td>
                <td><label for="pick38"></label><input type="text" id="pick38" name="pick38" size = "3 px" ${activeDraft.picks[37].locked ? 'disabled' : ''} value=${activeDraft.picks[37].pick}></td>
                <td><label for="pick39"></label><input type="text" id="pick39" name="pick39" size = "3 px" ${activeDraft.picks[38].locked ? 'disabled' : ''} value=${activeDraft.picks[38].pick}></td>
                <td><label for="pick40"></label><input type="text" id="pick40" name="pick40" size = "3 px" ${activeDraft.picks[30].locked ? 'disabled' : ''} value=${activeDraft.picks[39].pick}></td>
            </tr>
            <tr>
                <td><label for="pick48"></label><input type="text" id="pick48" name="pick48" size = "3 px" ${activeDraft.picks[47].locked ? 'disabled' : ''} value=${activeDraft.picks[47].pick}></td>
                <td><label for="pick47"></label><input type="text" id="pick47" name="pick47" size = "3 px" ${activeDraft.picks[46].locked ? 'disabled' : ''} value=${activeDraft.picks[46].pick}></td>
                <td><label for="pick46"></label><input type="text" id="pick46" name="pick46" size = "3 px" ${activeDraft.picks[45].locked ? 'disabled' : ''} value=${activeDraft.picks[45].pick}></td>
                <td><label for="pick45"></label><input type="text" id="pick45" name="pick45" size = "3 px" ${activeDraft.picks[44].locked ? 'disabled' : ''} value=${activeDraft.picks[44].pick}></td>
                <td><label for="pick44"></label><input type="text" id="pick44" name="pick44" size = "3 px" ${activeDraft.picks[43].locked ? 'disabled' : ''} value=${activeDraft.picks[43].pick}></td>
                <td><label for="pick43"></label><input type="text" id="pick43" name="pick43" size = "3 px" ${activeDraft.picks[42].locked ? 'disabled' : ''} value=${activeDraft.picks[42].pick}></td>
                <td><label for="pick42"></label><input type="text" id="pick42" name="pick42" size = "3 px" ${activeDraft.picks[41].locked ? 'disabled' : ''} value=${activeDraft.picks[41].pick}></td>
                <td><label for="pick41"></label><input type="text" id="pick41" name="pick41" size = "3 px" ${activeDraft.picks[40].locked ? 'disabled' : ''} value=${activeDraft.picks[40].pick}></td>
            </tr>
            <tr>
                <td><label for="pick49"></label><input type="text" id="pick49" name="pick49" size = "3 px" ${activeDraft.picks[48].locked ? 'disabled' : ''} value=${activeDraft.picks[48].pick}></td>
                <td><label for="pick50"></label><input type="text" id="pick50" name="pick50" size = "3 px" ${activeDraft.picks[49].locked ? 'disabled' : ''} value=${activeDraft.picks[49].pick}></td>
                <td><label for="pick51"></label><input type="text" id="pick51" name="pick51" size = "3 px" ${activeDraft.picks[50].locked ? 'disabled' : ''} value=${activeDraft.picks[50].pick}></td>
                <td><label for="pick52"></label><input type="text" id="pick52" name="pick52" size = "3 px" ${activeDraft.picks[51].locked ? 'disabled' : ''} value=${activeDraft.picks[51].pick}></td>
                <td><label for="pick53"></label><input type="text" id="pick53" name="pick53" size = "3 px" ${activeDraft.picks[52].locked ? 'disabled' : ''} value=${activeDraft.picks[52].pick}></td>
                <td><label for="pick54"></label><input type="text" id="pick54" name="pick54" size = "3 px" ${activeDraft.picks[53].locked ? 'disabled' : ''} value=${activeDraft.picks[53].pick}></td>
                <td><label for="pick55"></label><input type="text" id="pick55" name="pick55" size = "3 px" ${activeDraft.picks[54].locked ? 'disabled' : ''} value=${activeDraft.picks[54].pick}></td>
                <td><label for="pick56"></label><input type="text" id="pick56" name="pick56" size = "3 px" ${activeDraft.picks[55].locked ? 'disabled' : ''} value=${activeDraft.picks[55].pick}></td>
            </tr>
            <tr>
                <td><label for="pick64"></label><input type="text" id="pick64" name="pick64" size = "3 px" ${activeDraft.picks[63].locked ? 'disabled' : ''} value=${activeDraft.picks[63].pick}></td>
                <td><label for="pick63"></label><input type="text" id="pick63" name="pick63" size = "3 px" ${activeDraft.picks[62].locked ? 'disabled' : ''} value=${activeDraft.picks[62].pick}></td>
                <td><label for="pick62"></label><input type="text" id="pick62" name="pick62" size = "3 px" ${activeDraft.picks[61].locked ? 'disabled' : ''} value=${activeDraft.picks[61].pick}></td>
                <td><label for="pick61"></label><input type="text" id="pick61" name="pick61" size = "3 px" ${activeDraft.picks[60].locked ? 'disabled' : ''} value=${activeDraft.picks[60].pick}></td>
                <td><label for="pick60"></label><input type="text" id="pick60" name="pick60" size = "3 px" ${activeDraft.picks[59].locked ? 'disabled' : ''} value=${activeDraft.picks[59].pick}></td>
                <td><label for="pick59"></label><input type="text" id="pick59" name="pick59" size = "3 px" ${activeDraft.picks[58].locked ? 'disabled' : ''} value=${activeDraft.picks[58].pick}></td>
                <td><label for="pick58"></label><input type="text" id="pick58" name="pick58" size = "3 px" ${activeDraft.picks[57].locked ? 'disabled' : ''} value=${activeDraft.picks[57].pick}></td>
                <td><label for="pick57"></label><input type="text" id="pick57" name="pick57" size = "3 px" ${activeDraft.picks[56].locked ? 'disabled' : ''} value=${activeDraft.picks[56].pick}></td>
            </tr>
        </table>

        <input type="submit" value="Submit">
    </form>
</div>

</body>
</html>
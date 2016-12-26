<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>User list</h2>
<form method="post" action="meals">
    <label>
        <select size = "3" multiple name = "user">
            <option disabled>Vibor Uzera</option>
            <option selected value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
        </select>
    </label>
    <button type="submit">Save</button>
</form>
</body>
</html>

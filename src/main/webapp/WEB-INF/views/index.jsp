<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:body>
        <div class="row mt-4">
            <div class="col-6 offset-3">
                <div class="card">
                    <h3 class="card-header">Welcome to my bank thing</h3>
                    <div class="card-body">
                        <form action="/account/login" class="form-inline" method="POST">  
                            <input type="text" class="form-control mr-2" id="name" name="name" placeholder="Enter your name">
                            <button type="submit" class="btn btn-success">Login</button>
                        </form>
                        <div class="mt-5">
                            <a href="/account/open" class="btn btn-primary">Create a new account</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:layout>
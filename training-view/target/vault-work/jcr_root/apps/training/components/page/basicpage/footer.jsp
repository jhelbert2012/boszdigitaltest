<%--

  Simple footer.

  ==============================================================================

--%><%@include file="/libs/foundation/global.jsp" %>
<div class="footer-container">
    <div class="disclaimer-container">
        <cq:include path="/content/training-landingpage/jcr:content/footer/disclaimer" resourceType="foundation/components/text" />
    </div>
    <div class="links-container">
	    <cq:include path="/content/training-landingpage/jcr:content/footer/home" resourceType="foundation/components/text" />
	    <cq:include path="/content/training-landingpage/jcr:content/footer/contactus" resourceType="foundation/components/text" />
    </div>
</div>
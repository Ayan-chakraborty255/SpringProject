import React from "react";

const Stats = () => {
  return (
    <div className="container p-3">
      <div className="row align-items-center p-3">
        {/* Text Section */}
        <div className="col-12 col-md-6 mt-5 p-3">
          <h1 className="fs-2 mb-5">Trust with confidence</h1>

          <h2 className="fs-4">Customer-first always</h2>
          <p className="text-muted">
            That's why 1.6+ crore customers trust us with ~ ₹6 lakh crores of
            equity investments, making us India’s largest broker; contributing
            to 15% of daily retail exchange volumes in India.
          </p>

          <h2 className="fs-4">No spam or gimmicks</h2>
          <p className="text-muted">
            No gimmicks, spam, "gamification", or annoying push notifications.
            High quality apps that you use at your pace, the way you like.
          </p>

          <h2 className="fs-4">Our universe</h2>
          <p className="text-muted">
            Not just an app, but a whole ecosystem. Our investments in 30+
            fintech startups offer you tailored services specific to your needs.
          </p>

          <h2 className="fs-4">Do better with money</h2>
          <p className="text-muted">
            With initiatives like Nudge and Kill Switch, we don't just
            facilitate transactions, but actively help you do better with your
            money.
          </p>
        </div>

        {/* Image Section */}
        <div className="col-12 col-md-6 p-3 text-center">
          <img
            src="media/images/ecoSystem.png"
            alt="ecosystem"
            className="img-fluid rounded"
            style={{ maxWidth: "100%", height: "auto" }}
          />
          <div className="text-center mt-3">
            <a
              href="#"
              className="mx-3 d-block d-md-inline text-decoration-none fw-semibold"
            >
              Explore our products <i className="fa-solid fa-arrow-right"></i>
            </a>
            <a
              href="#"
              className="mx-3 d-block d-md-inline text-decoration-none fw-semibold"
            >
              Try Kite demo <i className="fa-solid fa-arrow-right"></i>
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Stats;
